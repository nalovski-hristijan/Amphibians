package com.hnalovski.amphibians.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hnalovski.amphibians.data.DataOrException
import com.hnalovski.amphibians.model.Amphibian
import com.hnalovski.amphibians.repository.AmphibianRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: AmphibianRepository) :
    ViewModel() {

    private val _amphibianData =
        mutableStateOf(DataOrException<List<Amphibian>, Boolean, Exception>(loading = true))
    val amphibianData = _amphibianData

    init {
        getAmphibian()
    }

    fun getAmphibian() {
        viewModelScope.launch {
            _amphibianData.value = repository.getAmphibians()
        }
    }

}