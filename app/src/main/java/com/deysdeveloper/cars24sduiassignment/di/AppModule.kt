package com.deysdeveloper.cars24sduiassignment.di

import com.deysdeveloper.cars24sduiassignment.data.repository.SduiRepository
import com.deysdeveloper.cars24sduiassignment.data.repository.SduiRepositoryImpl
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindSduiRepository(impl: SduiRepositoryImpl): SduiRepository

    companion object {
        @Provides
        @Singleton
        fun provideGson(): Gson = Gson()
    }
}
