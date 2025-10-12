package com.teamsolply.solply.mypage.model

import kotlinx.serialization.Serializable

@Serializable
enum class WithdrawType(
    val description: String
) {
    NOT_USE("자주 사용하지 않아서"),
    DEFICIENT_INFO("원하는 지역과 장소가 부족해서"),
    INCONVENIENT("앱 기능이 불편해서"),
    HATE_RECOMMEND("추천 콘텐츠가 나와 맞지 않아서"),
    USE_OTHER_SERVICE("다른 서비스를 사용하고 있습니다."),
    OTHERS("기타")
}
