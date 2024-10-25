package com.example.mvinoteappex.addNote.presentation

data class AddNoteState(
    val imageUrl: String = "",
    val title: String = "",
    val description: String = "",

    val isImagesDialogShowing: Boolean = false,

    val searchImagesQuery: String = "",
    val isLoadingImages: Boolean = false,
    val imageList: List<String> = emptyList()
)
