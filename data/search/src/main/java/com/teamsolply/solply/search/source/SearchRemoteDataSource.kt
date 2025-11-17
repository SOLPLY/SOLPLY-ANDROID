package com.teamsolply.solply.search.source

import com.teamsolply.solply.search.dto.request.RegisterPlaceRequestDto
import com.teamsolply.solply.search.dto.response.RegisterPlaceResponseDto
import com.teamsolply.solply.search.dto.response.SearchResponseDto

interface SearchRemoteDataSource {
    suspend fun getPlaceSearch(keyword: String): SearchResponseDto
    suspend fun requestsPlaces(registerPlaceRequestDto: RegisterPlaceRequestDto): RegisterPlaceResponseDto
}
