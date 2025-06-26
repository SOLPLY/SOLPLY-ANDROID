package com.teamsolply.solply.mypage.service

import com.teamsolply.solply.mypage.dto.response.UserInfoResponseDto
import com.teamsolply.solply.network.model.BaseResponse

interface MypageService {
    suspend fun getUserInfo(): BaseResponse<UserInfoResponseDto>
}