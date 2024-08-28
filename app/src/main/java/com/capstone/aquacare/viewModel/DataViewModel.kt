package com.capstone.aquacare.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.aquacare.data.AquascapeData
import com.capstone.aquacare.data.ArticleData
import com.capstone.aquacare.data.IdentificationData
import com.capstone.aquacare.data.Repository
import kotlinx.coroutines.launch

class DataViewModel(private val repository: Repository): ViewModel() {

    // Data daftar aquascape
    private val _aquascapeData = MutableLiveData<List<AquascapeData>>()
    val aquascapeData: LiveData<List<AquascapeData>> get() = _aquascapeData

    // Data daftar artikel
    private val _articleData = MutableLiveData<List<ArticleData>>()
    val articleData: LiveData<List<ArticleData>> get() = _articleData

    // Data daftar riwayat identifikasi
    private val _identificationData = MutableLiveData<List<IdentificationData>>()
    val identificationData: LiveData<List<IdentificationData>> get() = _identificationData

    // Loading daftar aquascape
    private val _isLoadingA = MutableLiveData<Boolean>()
    val isLoadingA: LiveData<Boolean> get() = _isLoadingA

    // Loading daftar artikel
    private val _isLoadingB = MutableLiveData<Boolean>()
    val isLoadingB: LiveData<Boolean> get() = _isLoadingB

    // Loading daftar riwayat identifikasi
    private val _isLoadingC = MutableLiveData<Boolean>()
    val isLoadingC: LiveData<Boolean> get() = _isLoadingB

    // Status hasil menambah aquascape
    private val _isSuccessA = MutableLiveData<Boolean>()
    val isSuccessA: LiveData<Boolean> get() = _isSuccessA

    // Status hasil mengubah aquascape
    private val _isSuccessB = MutableLiveData<Boolean>()
    val isSuccessB: LiveData<Boolean> get() = _isSuccessB

    // Status hasil menghapus aquascape
    private val _isSuccessC = MutableLiveData<Boolean>()
    val isSuccessC: LiveData<Boolean> get() = _isSuccessC

    // Mengambil daftar aquascape
    fun getAquascapeData(userId: String) {
        _isLoadingA.value = true

        viewModelScope.launch {
            try {
                val aquascapeList = repository.getAquascapeData(userId)
                _aquascapeData.postValue(aquascapeList)
            } catch (e: Exception) {
                Log.e("DataViewModel", "Error to retrieve aquascape data: ${e.message}")
            } finally {
                _isLoadingA.value = false
            }
        }
    }

    // Mengambil daftar artikel
    fun getArticleData() {
        _isLoadingB.value = true

        viewModelScope.launch {
            try {
                val articleList = repository.getArticleData()
                _articleData.postValue(articleList)
            } catch (e: Exception) {
                Log.e("DataViewModel", "Error to retrieve article data: ${e.message}")
            } finally {
                _isLoadingB.value = false
            }
        }
    }

    // Mengambil daftar riwayat identifikasi
    fun getIdentificationData(userId : String, aquascapeId : String) {
        _isLoadingC.value = true

        viewModelScope.launch {
            try {
                val identificationList = repository.getIdentificationData(userId, aquascapeId)
                _identificationData.postValue(identificationList)
            } catch (e: Exception) {
                Log.e("DataViewModel", "Error to retrieve identification history data: ${e.message}")
            } finally {
                _isLoadingC.value = false
            }
        }
    }

    // Menambahkan data aquascape baru
    fun addNewAquascape(userId : String, name: String, style: String, date: String) {
        viewModelScope.launch {
            val newAquascapeData = AquascapeData("", name, style, date, "", "")
            val result = repository.addNewAquascape(userId, newAquascapeData)

            result.onSuccess {
                _isSuccessA.value = true
            }.onFailure {
                _isSuccessA.value = false
            }
        }
    }

    // Mengubah data Aquascape
    fun editAquascape(userId: String, aquascapeId: String, name: String, style: String) {
        viewModelScope.launch {
            val result = repository.editAquascape(userId, aquascapeId, name, style)
            result.onSuccess {
                _isSuccessB.value = true
            }.onFailure {
                _isSuccessB.value = false
            }
        }
    }

    // Menghapus data aquascape
    fun deleteAquascape(userId: String, aquascapeId: String) {
        viewModelScope.launch {
            val result = repository.deleteAquascape(userId, aquascapeId)
            result.onSuccess {
                _isSuccessC.value = true
            }.onFailure {
                _isSuccessC.value = false
            }
        }
    }

}