package com.deysdeveloper.cars24sduiassignment.data.repository

import com.deysdeveloper.cars24sduiassignment.data.model.SduiPage
import com.deysdeveloper.cars24sduiassignment.data.source.SduiDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SduiRepositoryImpl @Inject constructor(
    private val dataSource: SduiDataSource
) : SduiRepository {

    override suspend fun getHomePage(): Result<SduiPage> = withContext(Dispatchers.IO) {
        runCatching { dataSource.getHomePage() }
    }
}
