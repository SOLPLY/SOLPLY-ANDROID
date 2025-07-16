package com.teamsolply.solply.model

enum class PlaceType(val displayName: String) {
    ALL("전체"),
    CAFE("카페"),
    FOOD("음식"),
    BOOKSTORE("서점/책방"),
    WALKING("산책"),
    SHOPPING("쇼핑"),
    UNIQUE_SPACE("이색공간");

    companion object {
        fun fromApiName(displayName: String): PlaceType = when (displayName) {
            "카페" -> CAFE
            "음식" -> FOOD
            "서점/책방" -> BOOKSTORE
            "산책" -> WALKING
            "쇼핑" -> SHOPPING
            "이색공간" -> UNIQUE_SPACE
            else -> ALL
        }
    }
}

enum class PlaceSubType(val displayName: String) {
    COFFEE_DESSERT("커피/디저트"),
    WORK("작업"),
    READING("독서"),
    HEALING("힐링"),
    SIGNATURE_MENU("시그니처 메뉴"),
    MOOD_INTERIOR("감성 인테리어"),
    SUNLIGHT("채광 좋음"),
    MANY_PLUG("콘센트 많음"),
    NO_TIME_LIMIT("시간 제한 없음"),
    BAR_TABLE("바테이블"),

    KOREAN_FOOD("한식"),
    CHINESE_FOOD("중식"),
    JAPANESE_FOOD("일식"),
    WESTERN_FOOD("양식"),
    BAR("바/술집"),
    BAKERY("베이커리"),
    ASIAN_FOOD("아시안 푸드"),
    SINGLE_MENU("1인 메뉴"),
    SELF_SERVICE("셀프 서비스"),

    ART("문화예술"),
    WORKSHOP("공방/클래스"),

    LIFESTYLE_SHOP("소품샵"),
    VINTAGE_SHOP("빈티지샵"),
    POPUP_MARKET("팝업/플리마켓"),

    UNKNOWN("알수없음");
}
