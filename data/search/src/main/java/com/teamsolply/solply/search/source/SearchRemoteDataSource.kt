package com.teamsolply.solply.search.source

import com.teamsolply.solply.search.dto.SearchResponseDto

interface SearchRemoteDataSource {
    suspend fun getPlaceSearch(keyword: String): SearchResponseDto
}
