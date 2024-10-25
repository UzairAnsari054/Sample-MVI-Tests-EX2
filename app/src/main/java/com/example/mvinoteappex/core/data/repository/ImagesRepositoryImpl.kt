package com.example.mvinoteappex.core.data.repository

import com.example.mvinoteappex.core.data.mapper.toImages
import com.example.mvinoteappex.core.data.remote.api.ImagesApi
import com.example.mvinoteappex.core.domain.model.Images
import com.example.mvinoteappex.core.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesApi: ImagesApi
) : ImagesRepository {
    override suspend fun searchImages(query: String): Images? {
        return imagesApi.searchImages(query)?.toImages()
    }
}