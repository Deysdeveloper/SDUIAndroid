package com.deysdeveloper.cars24sduiassignment.ui.state

import com.deysdeveloper.cars24sduiassignment.data.model.SduiPage

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val page: SduiPage) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
