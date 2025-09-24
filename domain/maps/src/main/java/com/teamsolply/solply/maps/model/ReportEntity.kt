package com.teamsolply.solply.maps.model

data class ReportRequestEntity(
    val reportType: String,
    val content: String,
    val imageKeys: List<String>
)

data class ReportResponseEntity(
    val reportId: Long,
    val status: String
)