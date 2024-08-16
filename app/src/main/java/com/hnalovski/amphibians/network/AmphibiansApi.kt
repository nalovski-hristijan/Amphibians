package com.hnalovski.amphibians.network

import com.hnalovski.amphibians.model.Amphibian
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface AmphibiansApi {
    @GET(value = "amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}