package com.hnalovski.amphibians.di

import com.hnalovski.amphibians.network.AmphibiansApi
import com.hnalovski.amphibians.repository.AmphibianRepository
import com.hnalovski.amphibians.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAmphibianApi(): AmphibiansApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AmphibiansApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAmphibianRepository(api: AmphibiansApi) = AmphibianRepository(api)
}