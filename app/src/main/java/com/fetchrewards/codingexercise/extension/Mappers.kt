package com.fetchrewards.codingexercise.extension

import com.fetchrewards.codingexercise.data.Hiring
import com.fetchrewards.codingexercise.data.HiringsUiState
import com.fetchrewards.codingexercise.data.hiringsDefaultState
import com.fetchrewards.codingexercise.repository.remoteDataSource.ApiHiring
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun ApiHiring.toHiring() = Hiring(listId = this.listId.toString(), name = this.name ?: "")

fun List<ApiHiring>.formatResult() = this
    .map { it.toHiring() }
    .filter { it.name.startsWith("Item ") }
    .sortedBy { it.name.removePrefix("Item ").toInt() }
    .groupBy { it.listId }
    .toSortedMap()

fun MutableStateFlow<HiringsUiState>.updateState(
    isFetchingData: Boolean = false,
    hirings: Map<String, List<Hiring>> = hiringsDefaultState,
    errorMessageId: Int = 0
) {
    this.update {
        it.copy(
            isFetchingData = isFetchingData,
            hirings = hirings,
            errorMessageId = errorMessageId
        )
    }
}
