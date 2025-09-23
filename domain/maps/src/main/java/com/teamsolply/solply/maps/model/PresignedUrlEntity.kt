package com.teamsolply.solply.maps.model

data class PresignedUrlsRequestEntity(
    val files: List<File>
)

data class File(
    val fileName: String
)

data class PresignedUrlsResponseEntity(
    val presignedUrlInfos: List<PresignedUrlInfo>
)

data class PresignedUrlInfo(
    val originalFileName: String,
    val tempFileKey: String,
    val presignedUrl: String,
    val expirationSeconds: Int
)