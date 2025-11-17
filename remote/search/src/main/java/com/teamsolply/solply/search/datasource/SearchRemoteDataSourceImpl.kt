package com.teamsolply.solply.search.datasource

import com.teamsolply.solply.search.dto.request.RegisterPlaceRequestDto
import com.teamsolply.solply.search.dto.response.SearchResponseDto
import com.teamsolply.solply.search.service.SearchService
import com.teamsolply.solply.search.source.SearchRemoteDataSource
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchService: SearchService
) : SearchRemoteDataSource {
    override suspend fun getPlaceSearch(keyword: String): SearchResponseDto =
        searchService.getPlaceSearch(keyword = keyword).data

    override suspend fun requestsPlaces(registerPlaceRequestDto: RegisterPlaceRequestDto) =
        searchService.requestsPlaces(registerPlaceRequestDto = registerPlaceRequestDto).data
}
