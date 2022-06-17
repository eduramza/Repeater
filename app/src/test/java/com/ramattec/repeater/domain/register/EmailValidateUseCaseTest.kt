package com.ramattec.repeater.domain.register

import org.junit.Assert.*
import org.junit.Test

class EmailValidateUseCaseTest{
    private val useCase = EmailValidateUseCase()

    @Test
    fun `Should return false when email is empty`(){
        val email = ""
        assertFalse(useCase(email))
    }

    @Test
    fun `Should return false when email is not match`(){
        val email = "eduardo"
        assertFalse(useCase(email))
    }

    @Test
    fun `Should return true when Emails is match`(){
        val email = "eduardo@gmail.com"
        assertTrue(useCase(email))
    }
}