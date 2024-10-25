package com.example.mvinoteappex.addNote.presentation

sealed interface AddNoteAction {
    data class UpdateTitle(val newTitle: String) : AddNoteAction
    data class UpdateDescription(val newDescription: String) : AddNoteAction

    data object UpdateSearchImageDialogVisibility : AddNoteAction
    data class UpdateSearchImageQuery(val newSearchQuery: String) : AddNoteAction
    data class PickImage(val imageUrl: String) : AddNoteAction

    data object SaveNote : AddNoteAction

}