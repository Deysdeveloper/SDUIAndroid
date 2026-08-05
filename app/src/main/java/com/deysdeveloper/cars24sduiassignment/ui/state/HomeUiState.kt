package com.deysdeveloper.cars24sduiassignment.ui.state

// Placeholder — replaced in Task 6 (HomeViewModel)
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val screenJson: String) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
