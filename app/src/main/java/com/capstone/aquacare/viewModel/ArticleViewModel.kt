package com.capstone.aquacare.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.aquacare.data.ArticleData
import com.capstone.aquacare.data.Repository
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: Repository): ViewModel() {

    // Data daftar artikel
    private val _articleData = MutableLiveData<List<ArticleData>>()
    val articleData: LiveData<List<ArticleData>> get() = _articleData

    // Loading daftar artikel
    private val _isLoadingA = MutableLiveData<Boolean>()
    val isLoadingA: LiveData<Boolean> get() = _isLoadingA

    // Status hasil menambah article
    private val _isSuccessA = MutableLiveData<Boolean>()
    val isSuccessA: LiveData<Boolean> get() = _isSuccessA

    // Status hasil menghapus article
    private val _isSuccessB = MutableLiveData<Boolean>()
    val isSuccessB: LiveData<Boolean> get() = _isSuccessB

    // Mengambil daftar artikel
    fun getArticleData() {
        _isLoadingA.value = true

        viewModelScope.launch {
            try {
                val articleList = repository.getArticleData()
                _articleData.postValue(articleList)
            } catch (e: Exception) {
                Log.e("DataViewModel", "Error to retrieve article data: ${e.message}")
            } finally {
                _isLoadingA.value = false
            }
        }
    }

    // Menambahkan artikel baru
    fun addArticle(title: String, image: String, type: String, body: String, link: String) {
        viewModelScope.launch {
            val newArticleData = ArticleData("", title, image, type, body, link)
            val result = repository.addArticle(newArticleData)
            result.onSuccess {
                _isSuccessA.value = true
            }.onFailure {
                _isSuccessA.value = false
            }
        }
    }

    // Menghapus artikel
    fun deleteArticle(articleId: String) {
        viewModelScope.launch {
            val result = repository.deleteArticle(articleId)
            result.onSuccess {
                _isSuccessB.value = true
            }.onFailure {
                _isSuccessB.value = false
            }
        }
    }
}