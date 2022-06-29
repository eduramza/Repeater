package com.ramattec.repeater.ui.deck

data class DeckUIState(
    val saveWithSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: Boolean = false,
    val deleteWithSuccess: Boolean = false
)
