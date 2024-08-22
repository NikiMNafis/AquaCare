package com.capstone.aquacare.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.data.Repository
import kotlinx.coroutines.launch

class DataViewModel(private val repository: Repository): ViewModel() {

    // Data daftar aquascape
    private val _aquascapeData = MutableLiveData<List<AquascapeData>>()
    val aquascapeData: LiveData<List<AquascapeData>> get() = _aquascapeData

    // Loading daftar aquascape
    private val _isLoadingA = MutableLiveData<Boolean>()
    val isLoadingA: LiveData<Boolean> get() = _isLoadingA

    // Mengambil daftar aquascape
    fun getAquascapeData(userId: String) {
        _isLoadingA.value = true

        viewModelScope.launch {
            try {
                val aquascapeList = repository.getAquascapeData(userId)
                _aquascapeData.postValue(aquascapeList)
            } catch (e: Exception) {
                Log.e("DataViewModel", "Error to retrieve aquascape data")
            } finally {
                _isLoadingA.value = false
            }
        }
    }
}