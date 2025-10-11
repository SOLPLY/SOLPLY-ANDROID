package com.teamsolply.solply.mypage.datasource

import android.util.Log
import com.teamsolply.solply.mypage.dto.request.DeleteUserRequestDto
import com.teamsolply.solply.mypage.dto.response.GetPersonaListResponseDto
import com.teamsolply.solply.mypage.dto.response.GetUserInfoResponseDto
import com.teamsolply.solply.mypage.dto.response.GetWithdrawListResponseDto
import com.teamsolply.solply.mypage.dto.response.NicknameDuplicateResponseDto
import com.teamsolply.solply.mypage.service.MypageService
import com.teamsolply.solply.network.model.NullableBaseResponse
import javax.inject.Inject

class MypageRemoteDataSourceImpl @Inject constructor(
    private val mypageService: MypageService
) : MypageRemoteDataSource {
    override suspend fun getUserInfo(): GetUserInfoResponseDto {
        val user = mypageService.getUserInfo().data
        return user
    }

    override suspend fun getPlaceList(townId: Long) =
        mypageService.getPlaceList(townId = townId).data

    override suspend fun getPersonaList(): GetPersonaListResponseDto =
        mypageService.getPersonaList().data

    override suspend fun checkNicknameDuplicate(nickname: String): NicknameDuplicateResponseDto =
        mypageService.checkNicknameDuplicate(nickname = nickname).data

    override suspend fun getWithdrawList(): GetWithdrawListResponseDto =
        mypageService.getWithdrawList().data

    override suspend fun deleteUser(deleteUserRequestDto: DeleteUserRequestDto) =
        mypageService.deleteUser(deleteUserRequestDto)
}
