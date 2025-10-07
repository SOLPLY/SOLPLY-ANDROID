package com.teamsolply.solply.model

import kotlinx.serialization.Serializable

@Serializable
enum class Persona(val description: String) {
    REST("조용한 공간에 오래 머물고 싶어요"),
    EXPLORER("이곳저곳 둘러보고 싶어요"),
    MOODING("취향이 담긴 곳을 찾고 싶어요"),
    NATURAL("자연을 감상하며 쉬고 싶어요"),
    ANYTHING("특별히 선호하는 공간은 없어요")
}
