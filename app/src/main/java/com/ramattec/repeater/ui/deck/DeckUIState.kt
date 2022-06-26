package com.ramattec.repeater.ui.deck

data class DeckUIState(
    val success: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: Boolean = false,
    val titleInvalid: String = "",
    val categoryInvalid: String = "",
    val descriptionInvalid: String = ""
)
