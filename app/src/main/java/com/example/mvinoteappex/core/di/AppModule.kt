package com.example.mvinoteappex.core.di

import android.app.Application
import androidx.room.PrimaryKey
import androidx.room.Room
import com.example.mvinoteappex.addNote.domain.use_case.SearchImages
import com.example.mvinoteappex.addNote.domain.use_case.UpsertNote
import com.example.mvinoteappex.core.data.local.NoteDatabase
import com.example.mvinoteappex.core.data.remote.api.ImagesApi
import com.example.mvinoteappex.core.data.repository.ImagesRepositoryImpl
import com.example.mvinoteappex.core.data.repository.NoteRepositoryImpl
import com.example.mvinoteappex.core.domain.repository.ImagesRepository
import com.example.mvinoteappex.core.domain.repository.NoteRepository
import com.example.mvinoteappex.note_list.domain.use_case.DeleteNote
import com.example.mvinoteappex.note_list.domain.use_case.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDatabase)
    }

    @Singleton
    @Provides
    fun proveGetAllNotesUseCase(noteRepository: NoteRepository): GetAllNotes {
        return GetAllNotes(noteRepository)
    }

    @Singleton
    @Provides
    fun proveDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNote {
        return DeleteNote(noteRepository)
    }


    @Singleton
    @Provides
    fun provideImagesApi(): ImagesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ImagesApi.BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideImagesRepository(imagesApi: ImagesApi): ImagesRepository {
        return ImagesRepositoryImpl(imagesApi)
    }

    @Singleton
    @Provides
    fun provideUpsertNoteUseCase(noteRepository: NoteRepository): UpsertNote {
        return UpsertNote(noteRepository)
    }

    @Singleton
    @Provides
    fun provideSearchImagesUseCase(imagesRepository: ImagesRepository): SearchImages {
        return SearchImages(imagesRepository)
    }
}