package com.teamsolply.solply.main.model

import com.teamsolply.solply.model.SnackBarType

data class SolplySnackBarData(
    val type: SnackBarType = SnackBarType.TEXT,
    val action: (() -> Unit)? = null
)