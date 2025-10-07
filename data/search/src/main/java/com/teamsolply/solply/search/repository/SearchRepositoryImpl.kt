package com.teamsolply.solply.search.repository

import androidx.core.text.HtmlCompat
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.network.service.NaverLocalSearchService
import com.teamsolply.solply.search.model.NaverLocalSearchResponseEntity
import com.teamsolply.solply.search.model.SearchResultEntity
import com.teamsolply.solply.search.source.SearchRemoteDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val naverLocalSearchService: NaverLocalSearchService
) : SearchRepository {
    override suspend fun getPlaceSearch(keyword: String): Result<List<SearchResultEntity>> =
        runCatching {
            searchRemoteDataSource.getPlaceSearch(keyword = keyword)
        }.mapCatching {
            it.places.map { searchResult ->
                SearchResultEntity(
                    placeId = searchResult.placeId,
                    placeName = searchResult.placeName,
                    thumbnailImageUrl = searchResult.thumbnailImageUrl,
                    primaryTag = PlaceType.fromApiCode(searchResult.primaryTag),
                    address = searchResult.address,
                    isBookmarked = searchResult.isBookmarked,
                    townId = searchResult.townId
                )
            }
        }

    override suspend fun searchAddress(query: String): Result<List<NaverLocalSearchResponseEntity>> =
        runCatching { naverLocalSearchService.searchLocal(query = query).items }
            .mapCatching {
                it.map { localSearchItem ->
                    NaverLocalSearchResponseEntity(
                        title = HtmlCompat.fromHtml(
                            localSearchItem.title,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString(),
                        link = localSearchItem.link,
                        category = localSearchItem.category,
                        description = localSearchItem.description,
                        telephone = localSearchItem.telephone,
                        address = localSearchItem.address,
                        roadAddress = localSearchItem.roadAddress,
                        mapx = localSearchItem.mapx,
                        mapy = localSearchItem.mapy
                    )
                }
            }
}
