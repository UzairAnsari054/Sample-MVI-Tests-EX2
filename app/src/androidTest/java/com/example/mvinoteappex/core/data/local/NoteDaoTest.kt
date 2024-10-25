package com.example.mvinoteappex.core.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.mvinoteappex.core.di.AppModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
@UninstallModules(AppModule::class)
class NoteDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        hiltRule.inject()
        noteDao = noteDatabase.getNoteDao()
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }

    @Test
    fun upsertNote_NoteEntityIsUpserted() = runTest {
        val noteEntity = NoteEntity(
            title = "title",
            description = "description",
            imageUrl = "imageUrl",
            dateAdded = System.currentTimeMillis(),
            id = 1
        )
        noteDao.upsertNoteEntity(noteEntity)
        assertThat(noteDao.loadNotesEntity().contains(noteEntity)).isTrue()
    }

    @Test
    fun deleteNote_noteEntityIsDeleted() = runTest {
        val noteEntity = NoteEntity(
            title = "title",
            description = "description",
            imageUrl = "imageUrl",
            dateAdded = System.currentTimeMillis(),
            id = 1
        )
        noteDao.upsertNoteEntity(noteEntity)
        assertThat(noteDao.loadNotesEntity().contains(noteEntity)).isTrue()

        noteDao.deleteNoteEntity(noteEntity)
        assertThat(!noteDao.loadNotesEntity().contains(noteEntity)).isTrue()
    }

    @Test
    fun getAllNotesEntitiesFromEmptyDb_noteListIsEmpty() = runTest {
        assertThat(noteDao.loadNotesEntity().isEmpty()).isTrue()
    }

    @Test
    fun getAllNotesEntitiesFromDb_noteListIsNotEmpty() = runTest {
        for (i in 1..4) {
            val noteEntity = NoteEntity(
                title = "Title $i",
                description = "Description $i",
                imageUrl = "ImageUrl $i",
                dateAdded = System.currentTimeMillis(),
                id = i
            )

            noteDao.upsertNoteEntity(noteEntity)
        }

        assertThat(noteDao.loadNotesEntity().isNotEmpty()).isTrue()
    }

}