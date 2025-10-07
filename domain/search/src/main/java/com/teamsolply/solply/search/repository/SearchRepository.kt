package com.teamsolply.solply.search.repository

import com.teamsolply.solply.search.model.NaverLocalSearchResponseEntity
import com.teamsolply.solply.search.model.SearchResultEntity

interface SearchRepository {
    suspend fun getPlaceSearch(keyword: String): Result<List<SearchResultEntity>>
    suspend fun searchAddress(query: String): Result<List<NaverLocalSearchResponseEntity>>
}
