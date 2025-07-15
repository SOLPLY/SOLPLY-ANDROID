package com.teamsolply.solply.maps.service

import com.teamsolply.solply.maps.dto.response.GetPlaceDetailResponseDto
import com.teamsolply.solply.network.model.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MapsService {
    // 장소 상세

    // 장소 단건 정보
    @GET("api/places/{placeId}")
    suspend fun getPlaceDetail(
        @Path("placeId") placeId: Long
    ): BaseResponse<GetPlaceDetailResponseDto>

    // 장소 단건 북마크 저장
    @POST("api/places/{placeId}/bookmarks")
    suspend fun postPlaceBookMark(
        @Path("placeId") placeId: Long
    ): BaseResponse<Unit>

    // 장소 단건 북마크 삭제
    @DELETE("api/places/{placeId}/bookmarks")
    suspend fun deletePlaceBookMark(
        @Path("placeId") placeId: Long
    ): BaseResponse<Unit>

    // 내 코스에 추가
    //코스 전체 조회
}
