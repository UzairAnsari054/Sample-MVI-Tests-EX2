package com.example.mvinoteappex.core.data.remote.api

import com.example.mvinoteappex.core.data.remote.dto.ImageListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("api/")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("key") apiKey: String = API_KEY
    ): ImageListDto?

    companion object {
        const val BASE_URL = "https://pixabay.com/"
        const val API_KEY = "46696553-5e9819a49eebb791070b3f1d9"
    }
}
