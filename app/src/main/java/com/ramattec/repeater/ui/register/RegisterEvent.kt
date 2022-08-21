package com.ramattec.repeater.ui.register

import com.ramattec.domain.model.user.User

sealed class RegisterEvent {
    data class RegisterNewUser(val data: User) : RegisterEvent()
}