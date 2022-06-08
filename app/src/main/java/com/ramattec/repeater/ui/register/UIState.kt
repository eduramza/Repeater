package com.ramattec.repeater.ui.register

data class UIState(
    val success: UserRegistered? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class UserRegistered(
    val name: String
)