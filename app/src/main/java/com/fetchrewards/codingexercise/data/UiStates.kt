package com.fetchrewards.codingexercise.data

val hiringsDefaultState: Map<String, List<Hiring>> = mapOf("" to listOf())

data class HiringsUiState(
    val hirings: Map<String, List<Hiring>> = hiringsDefaultState,
    val isFetchingData: Boolean = false,
    val errorMessageId: Int = 0
)