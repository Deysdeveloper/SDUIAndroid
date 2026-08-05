package com.deysdeveloper.cars24sduiassignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// Placeholder — replaced in Task 6 (HomeViewModel)
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<String>("loading")
    val uiState: StateFlow<String> = _uiState.asStateFlow()
}
