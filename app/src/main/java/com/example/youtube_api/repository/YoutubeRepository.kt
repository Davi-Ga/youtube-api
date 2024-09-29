package com.example.youtube_api.repository

import com.example.youtube_api.network.NetworkModule
import com.example.youtube_api.network.YoutubeApiService

class YoutubeRepository {
    private val apiService: YoutubeApiService = NetworkModule.retrofit.create(YoutubeApiService::class.java)

    suspend fun searchVideos(query: String, apiKey: String, page: Int) = apiService.searchVideos("snippet", query, apiKey, page)
}