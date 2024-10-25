package com.example.mvinoteappex.core.presentation

sealed interface Screen {
    @kotlinx.serialization.Serializable
    data object NoteList : Screen

    @kotlinx.serialization.Serializable
    data object AddNote : Screen
}