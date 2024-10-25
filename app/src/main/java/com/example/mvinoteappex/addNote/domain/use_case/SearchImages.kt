package com.example.mvinoteappex.addNote.domain.use_case

import com.example.mvinoteappex.addNote.presentation.utils.Resource
import com.example.mvinoteappex.core.domain.model.Images
import com.example.mvinoteappex.core.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchImages @Inject constructor(
    private val searchRepository: ImagesRepository
) {

    suspend operator fun invoke(query: String): Flow<Resource<Images>> {
        return flow {
            emit(Resource.Loading(true))

            if (query.isEmpty()) {
                emit(Resource.Error())
                emit(Resource.Loading(false))
                return@flow
            }

            val images = try {
                searchRepository.searchImages(query)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error())
                emit(Resource.Loading(false))
                return@flow
            }

            images?.let {
                emit(Resource.Success(data = it))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error())
            emit(Resource.Loading(false))
        }
    }
}