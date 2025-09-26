package com.teamsolply.solply.search.repository

import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.search.model.SearchResultEntity
import com.teamsolply.solply.search.source.SearchRemoteDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
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
                    isBookmarked = searchResult.isBookmarked
                )
            }
        }
}
