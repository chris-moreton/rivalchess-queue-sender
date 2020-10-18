package com.netsensia.rivalchess.generator

data class MatchRequestPayload(
    val engine1: String,
    val engine2: String,
    val nodesToSearch: Int?,
    val maxTimeMillis: Int?,
    val openingLibrary: String?
)
