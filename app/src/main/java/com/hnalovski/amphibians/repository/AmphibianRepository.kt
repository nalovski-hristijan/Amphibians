package com.hnalovski.amphibians.repository

import android.util.Log
import com.hnalovski.amphibians.data.DataOrException
import com.hnalovski.amphibians.model.Amphibian
import com.hnalovski.amphibians.network.AmphibiansApi
import javax.inject.Inject

class AmphibianRepository @Inject constructor(private val api: AmphibiansApi) {

    suspend fun getAmphibians(): DataOrException<List<Amphibian>, Boolean, Exception> {
        val response = try {
            api.getAmphibians()
        } catch (e: Exception) {
            Log.d("Exception", "getAmphibians: $e")
            return DataOrException(e = e)
        }

        Log.d("Amp", "getAmphibians: $response")

        return DataOrException(data = response)
    }
}