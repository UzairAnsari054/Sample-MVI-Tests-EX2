package com.example.mvinoteappex.core.domain.repository

import com.example.mvinoteappex.core.domain.model.Images

interface ImagesRepository {
    suspend fun searchImages(query: String): Images?
}