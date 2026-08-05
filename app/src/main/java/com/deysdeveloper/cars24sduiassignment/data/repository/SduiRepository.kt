package com.deysdeveloper.cars24sduiassignment.data.repository

import com.deysdeveloper.cars24sduiassignment.data.model.SduiPage

interface SduiRepository {
    suspend fun getHomePage(): Result<SduiPage>
}
