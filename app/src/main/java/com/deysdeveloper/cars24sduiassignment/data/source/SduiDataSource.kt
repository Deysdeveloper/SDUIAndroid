package com.deysdeveloper.cars24sduiassignment.data.source

import android.content.Context
import com.deysdeveloper.cars24sduiassignment.data.model.SduiPage
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SduiDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    fun getHomePage(): SduiPage {
        val json = context.assets.open("sdui_home.json")
            .bufferedReader()
            .use { it.readText() }
        return gson.fromJson(json, SduiPage::class.java)
    }
}
