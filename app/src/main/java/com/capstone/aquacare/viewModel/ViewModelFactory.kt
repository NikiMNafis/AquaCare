package com.capstone.aquacare.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.aquacare.data.Repository

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            modelClass.getConstructor(Repository::class.java).newInstance(repository) as T
        } catch (e: NoSuchMethodException) {
            throw IllegalArgumentException("ViewModel class must have a constructor with Repository parameter", e)
        } catch (e: Exception) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}


