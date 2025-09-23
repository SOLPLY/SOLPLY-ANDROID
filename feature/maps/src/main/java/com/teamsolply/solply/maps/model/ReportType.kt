package com.teamsolply.solply.maps.model

enum class ReportType(val content: String) {
    EMPTY(content = ""),
    STORE_CLOSED(content = "가게가 폐업했어요."),
    WRONG_ADDRESS(content = "잘못된 주소예요."),
    WRONG_PHONE(content = "잘못된 전화번호예요."),
    WRONG_HOURS(content = "운영시간과 휴무일이 잘못되었어요."),
    WRONG_CATEGORY(content = "카테고리 분류가 잘못되었어요."),
    OTHER(content = "기타"),
}