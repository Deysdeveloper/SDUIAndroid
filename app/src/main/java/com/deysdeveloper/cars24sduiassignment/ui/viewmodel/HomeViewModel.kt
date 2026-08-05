package com.deysdeveloper.cars24sduiassignment.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deysdeveloper.cars24sduiassignment.data.model.ScreenData
import com.deysdeveloper.cars24sduiassignment.ui.state.HomeUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val gson: Gson
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /**
     * Holds the set of component IDs currently visible due to a tab filter.
     * null means "All" — every component is shown.
     */
    private val _activeFilter = MutableStateFlow<Set<String>?>(null)
    val activeFilter: StateFlow<Set<String>?> = _activeFilter.asStateFlow()

    init {
        loadScreen()
    }

    fun loadScreen() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    val json = context.assets
                        .open("home_screen.json")
                        .bufferedReader()
                        .use { it.readText() }
                    gson.fromJson(json, ScreenData::class.java)
                }
            }
            result
                .onSuccess { _uiState.value = HomeUiState.Success(it) }
                .onFailure { _uiState.value = HomeUiState.Error(it.message ?: "Failed to load screen") }
        }
    }

    /**
     * Called by ActionHandler when a category tab fires a filter_sections action.
     * [ids] is a comma-separated string of component IDs from the JSON params.
     * Passing an empty string resets to "All".
     */
    fun filterSections(ids: String) {
        _activeFilter.value = if (ids.isBlank()) {
            null // show everything
        } else {
            ids.split(",").map { it.trim() }.toSet()
        }
    }
}
