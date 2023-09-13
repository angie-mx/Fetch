package com.fetchrewards.codingexercise.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fetchrewards.codingexercise.R
import com.fetchrewards.codingexercise.data.HiringsUiState
import com.fetchrewards.codingexercise.data.hiringsDefaultState
import com.fetchrewards.codingexercise.extension.updateState
import com.fetchrewards.codingexercise.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val mutableUiState = MutableStateFlow(HiringsUiState())
    val uiState: StateFlow<HiringsUiState> = mutableUiState.asStateFlow()

    init {
        viewModelScope.launch {
            mutableUiState.updateState(isFetchingData = true)
            val result = repository.loadHirings()
            when {
                result.isSuccess -> {
                    mutableUiState.updateState(
                        isFetchingData = false,
                        hirings = result.getOrDefault(hiringsDefaultState)
                    )
                }
                else -> {
                    mutableUiState.updateState(
                        isFetchingData = false,
                        errorMessageId = R.string.error_message
                    )
                }
            }
        }
    }
}
