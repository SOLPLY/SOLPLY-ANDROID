package com.teamsolply.solply.registerplace

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.teamsolply.solply.designsystem.component.card.getFileName
import com.teamsolply.solply.maps.model.File
import com.teamsolply.solply.maps.model.PresignedUrlsRequestEntity
import com.teamsolply.solply.maps.repository.MapsRepository
import com.teamsolply.solply.model.PlaceType.Companion.toId
import com.teamsolply.solply.search.model.PlaceImageEntity
import com.teamsolply.solply.search.model.RegisterPlaceEntity
import com.teamsolply.solply.search.repository.SearchRepository
import com.teamsolply.solply.ui.base.BaseViewModel
import com.teamsolply.solply.ui.util.uploadToPresignedUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPlaceViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val mapsRepository: MapsRepository,
    @ApplicationContext private val context: Context
) : BaseViewModel<RegisterPlaceState, RegisterPlaceIntent, RegisterPlaceSideEffect>(
    RegisterPlaceState()
) {
    private var searchJob: Job? = null

    override fun handleIntent(intent: RegisterPlaceIntent) {
        when (intent) {
            is RegisterPlaceIntent.InputPlaceNameText -> {
                reduce {
                    copy(placeName = intent.text)
                }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500)
                    if (intent.text.length >= 2) {
                        searchAddress(intent.text)
                    }
                }
            }

            is RegisterPlaceIntent.SelectPlaceName -> {
                reduce {
                    copy(
                        isPlaceNameSuccess = !isPlaceNameSuccess,
                        placeName = intent.placeName,
                        placeAddress = intent.placeAddress
                    )
                }
            }

            is RegisterPlaceIntent.ChangePlaceTypeDropDownVisible -> {
                reduce {
                    copy(
                        isPlaceTypeDropdownExpanded = !isPlaceTypeDropdownExpanded
                    )
                }
            }

            is RegisterPlaceIntent.ClickDropDownItem -> {
                reduce {
                    copy(
                        selectedPlaceType = intent.placeType,
                        isPlaceTypeDropdownExpanded = !isPlaceTypeDropdownExpanded
                    )
                }
            }

            is RegisterPlaceIntent.ClickPlaceKeyword -> {
                reduce {
                    val updatedKeywords =
                        if (selectedPlaceKeyword.contains(intent.placeKeywordId)) {
                            selectedPlaceKeyword - intent.placeKeywordId
                        } else {
                            selectedPlaceKeyword + intent.placeKeywordId
                        }
                    copy(selectedPlaceKeyword = updatedKeywords)
                }
            }

            is RegisterPlaceIntent.ClickPlaceFeature -> {
                reduce {
                    val updatedFeatures =
                        if (selectedPlaceFeature.contains(intent.placeFeatureId)) {
                            selectedPlaceFeature - intent.placeFeatureId
                        } else {
                            selectedPlaceFeature + intent.placeFeatureId
                        }
                    copy(selectedPlaceFeature = updatedFeatures)
                }
            }

            is RegisterPlaceIntent.InputRegisterPlaceReason -> reduce {
                copy(registerPlaceReason = intent.text)
            }

            is RegisterPlaceIntent.SelectedReportUris -> reduce {
                val current = selectedReportUris
                val remain = (3 - current.size).coerceAtLeast(0)
                val income = intent.uris.take(remain)
                copy(selectedReportUris = (current + income))
            }

            is RegisterPlaceIntent.ClickRegisterPlace -> registerPlace()

            is RegisterPlaceIntent.ChangeRegisterLottieVisibility -> reduce {
                if (intent.visible) {
                    copy(registerLottieVisibility = true)
                } else {
                    copy(
                        registerLottieVisibility = false,
                        isRegisterSuccess = false
                    )
                }
            }
        }
    }

    private fun searchAddress(query: String) {
        viewModelScope.launch {
            searchRepository.searchAddress(query = query)
                .onSuccess {
                    reduce {
                        copy(searchResults = it)
                    }
                }
        }
    }

    private fun registerPlace() {
        viewModelScope.launch(Dispatchers.IO) {
            reduce {
                copy(
                    registerLottieVisibility = true,
                    isRegisterSuccess = false
                )
            }
            // 이미지가 없는 경우
            if (uiState.value.selectedReportUris.isEmpty()) {
                searchRepository.requestsPlaces(
                    registerPlaceEntity = RegisterPlaceEntity(
                        placeName = uiState.value.placeName,
                        address = uiState.value.placeAddress,
                        mainTagId = uiState.value.selectedPlaceType.toId(),
                        subTagAIds = uiState.value.selectedPlaceKeyword,
                        subTagBIds = uiState.value.selectedPlaceFeature,
                        reason = uiState.value.registerPlaceReason,
                        images = emptyList()
                    )
                ).onSuccess {
                    android.util.Log.d("RegisterPlace", "✅ 장소 등록 성공 (이미지 없음)")
                    reduce {
                        copy(isRegisterSuccess = true)
                    }
                    delay(2500)
                    postSideEffect(RegisterPlaceSideEffect.NavigateToBack)
                    // TODO: 성공 처리
                }.onFailure { error ->
                    android.util.Log.e(
                        "RegisterPlace",
                        "❌ 장소 등록 실패 (이미지 없음): ${error.message}",
                        error
                    )
                    reduce {
                        copy(isRegisterSuccess = false)
                    }
                    delay(5000)
                    sendIntent(RegisterPlaceIntent.ChangeRegisterLottieVisibility(visible = false))
                    // TODO: 실패 처리
                }
                return@launch
            }

            // 이미지가 있는 경우
            val resolver = context.contentResolver
            val selectedFilesName = uiState.value.selectedReportUris.map {
                resolver.getFileName(it)
            }

            // 1. Presigned URL 발급
            mapsRepository.postPresignedUrl(
                presignedUrlsRequestEntity = PresignedUrlsRequestEntity(
                    files = selectedFilesName.map { File(it) }
                )
            ).onSuccess { response ->
                android.util.Log.d("RegisterPlace", "✅ Presigned URL 발급 성공")
                val infos = response.presignedUrlInfos
                val uris = uiState.value.selectedReportUris
                val byName = uris.associateBy { resolver.getFileName(it) }

                // 2. S3에 파일 업로드
                val result = runCatching {
                    coroutineScope {
                        infos.mapIndexed { index, info ->
                            val uri = byName[info.originalFileName]
                                ?: error("URI for ${info.originalFileName} not found")
                            async {
                                uploadToPresignedUrl(
                                    context = context,
                                    uri = uri,
                                    presignedUrl = info.presignedUrl
                                )
                                PlaceImageEntity(
                                    displayOrder = (index + 1).toLong(),
                                    tempFileKey = info.tempFileKey
                                )
                            }
                        }.awaitAll()
                    }
                }

                result.onSuccess { placeImages ->
                    android.util.Log.d("RegisterPlace", "✅ 모든 파일 업로드 완료")

                    // 3. 장소 등록 API 호출
                    searchRepository.requestsPlaces(
                        registerPlaceEntity = RegisterPlaceEntity(
                            placeName = uiState.value.placeName,
                            address = uiState.value.placeAddress,
                            mainTagId = uiState.value.selectedPlaceType.toId(),
                            subTagAIds = uiState.value.selectedPlaceKeyword,
                            subTagBIds = uiState.value.selectedPlaceFeature,
                            reason = uiState.value.registerPlaceReason,
                            images = placeImages
                        )
                    ).onSuccess {
                        android.util.Log.d("RegisterPlace", "✅ 장소 등록 완료!")
                        reduce {
                            copy(isRegisterSuccess = true)
                        }
                        delay(2500)
                        postSideEffect(RegisterPlaceSideEffect.NavigateToBack)
                        // TODO: 성공 처리
                    }.onFailure { error ->
                        android.util.Log.e(
                            "RegisterPlace",
                            "❌ 장소 등록 API 실패: ${error.message}",
                            error
                        )
                        reduce {
                            copy(isRegisterSuccess = false)
                        }
                        delay(5000)
                        sendIntent(RegisterPlaceIntent.ChangeRegisterLottieVisibility(visible = false))
                        // TODO: 실패 처리
                    }
                }.onFailure { error ->
                    android.util.Log.e("RegisterPlace", "❌ 파일 업로드 실패: ${error.message}", error)
                    reduce {
                        copy(isRegisterSuccess = false)
                    }
                    delay(5000)
                    sendIntent(RegisterPlaceIntent.ChangeRegisterLottieVisibility(visible = false))
                    // TODO: 업로드 실패 처리
                }
            }.onFailure { error ->
                android.util.Log.e(
                    "RegisterPlace",
                    "❌ Presigned URL 발급 실패: ${error.message}",
                    error
                )
                reduce {
                    copy(isRegisterSuccess = false)
                }
                delay(5000)
                sendIntent(RegisterPlaceIntent.ChangeRegisterLottieVisibility(visible = false))
                // TODO: presigned url 발급 실패 처리
            }
        }
    }
}
