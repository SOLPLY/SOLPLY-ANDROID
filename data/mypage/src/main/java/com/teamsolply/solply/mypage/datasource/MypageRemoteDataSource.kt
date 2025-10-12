package com.teamsolply.solply.mypage.datasource

import com.teamsolply.solply.mypage.dto.request.DeleteUserRequestDto
import com.teamsolply.solply.mypage.dto.response.GetPersonaListResponseDto
import com.teamsolply.solply.mypage.dto.response.GetUserInfoResponseDto
import com.teamsolply.solply.mypage.dto.response.GetWithdrawListResponseDto
import com.teamsolply.solply.mypage.dto.response.NicknameDuplicateResponseDto
import com.teamsolply.solply.mypage.dto.response.PlaceListResponseDto
import com.teamsolply.solply.network.model.NullableBaseResponse

interface MypageRemoteDataSource {
    suspend fun getUserInfo(): GetUserInfoResponseDto
    suspend fun getPlaceList(townId: Long): PlaceListResponseDto
    suspend fun getPersonaList(): GetPersonaListResponseDto
    suspend fun checkNicknameDuplicate(nickname: String): NicknameDuplicateResponseDto
    suspend fun getWithdrawList(): GetWithdrawListResponseDto
    suspend fun deleteUser(deleteUserRequestDto: DeleteUserRequestDto): NullableBaseResponse<Unit>
    suspend fun getReportPlaceList(userId: Long): PlaceListResponseDto
}
