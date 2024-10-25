package com.example.mvinoteappex.core.data.mapper

import com.example.mvinoteappex.core.data.remote.dto.ImageListDto
import com.example.mvinoteappex.core.domain.model.Images

fun ImageListDto.toImages(): Images {
    return Images(
        images = hits?.map { it.previewURL ?: "" } ?: emptyList()
    )
}