package com.deysdeveloper.cars24sduiassignment.ui.state

import com.deysdeveloper.cars24sduiassignment.data.model.ScreenData

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val screen: ScreenData) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
