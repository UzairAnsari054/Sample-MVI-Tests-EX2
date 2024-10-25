package com.example.mvinoteappex.core.data.repository

import com.example.mvinoteappex.core.domain.model.Images
import com.example.mvinoteappex.core.domain.repository.ImagesRepository


class FakeImagesRepository : ImagesRepository {

    private var shouldReturnError = false
    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun searchImages(query: String): Images? {
        return if (shouldReturnError) {
            null
        } else {
            Images(listOf("Image1", "Image2", "Image3", "Image4"))
        }
    }

}