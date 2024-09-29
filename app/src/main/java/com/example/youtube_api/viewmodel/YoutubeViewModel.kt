package com.example.youtube_api.viewmodel

import retrofit2.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtube_api.network.VideoItem
import com.example.youtube_api.repository.YoutubeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class YoutubeViewModel(private val repository: YoutubeRepository) : ViewModel() {
    private val _videos = MutableStateFlow<List<VideoItem>>(emptyList())
    val videos: StateFlow<List<VideoItem>> = _videos

    private var currentPage = 1
    private var isLoading = false

    fun searchVideos(query: String, apiKey: String, page: Int = 1) {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                val response = repository.searchVideos(query, apiKey, page)
                _videos.value = if (page == 1) {
                    response.items
                } else {
                    _videos.value + response.items
                }
                currentPage = page
            } catch (e: HttpException) {
                e.printStackTrace()
                println("HTTP error: ${e.response()?.errorBody()?.string()}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun loadNextPage(query: String, apiKey: String) {
        searchVideos(query, apiKey, currentPage + 1)
    }
}