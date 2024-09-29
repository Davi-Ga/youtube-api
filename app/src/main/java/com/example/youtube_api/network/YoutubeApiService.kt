package com.example.youtube_api.network

import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {
    @GET("search")
    suspend fun searchVideos(
        @Query("part") part: String,
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("page") page: Int
    ): SearchResponse
}

data class SearchResponse(
    val items: List<VideoItem>
)

data class VideoItem(
    val id: VideoId,
    val snippet: Snippet
)

data class VideoId(
    val videoId: String
)

data class Snippet(
    val title: String,
    val description: String,
    val thumbnails: Thumbnails
)

data class Thumbnails(
    val default: Thumbnail
)

data class Thumbnail(
    val url: String
)