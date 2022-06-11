package com.ramattec.repeater.domain.login

import com.ramattec.repeater.data.auth.AuthRepository
import javax.inject.Inject

class EmailPasswordLoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.doLoginWithEmailAndPassword(email, password)
}