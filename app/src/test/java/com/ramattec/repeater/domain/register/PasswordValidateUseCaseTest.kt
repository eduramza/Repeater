package com.ramattec.repeater.domain.register

import org.junit.Assert.*
import org.junit.Test

class PasswordValidateUseCaseTest{
    private val passwordValidateUseCase = PasswordValidateUseCase()

    @Test
    fun `Should return false when password is not match`(){
        val password = "aq1"
        assertFalse(passwordValidateUseCase(password))
    }

    @Test
    fun `Should return true when password contains, letters, numbers and 8 chars`(){
        val pass = "EDU123456"
        assertTrue(passwordValidateUseCase(pass))
    }
}