package com.example.youtube_api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youtube_api.repository.YoutubeRepository

class YoutubeViewModelFactory(private val repository: YoutubeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YoutubeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return YoutubeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}