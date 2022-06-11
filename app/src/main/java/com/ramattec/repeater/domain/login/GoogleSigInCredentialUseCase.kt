package com.ramattec.repeater.domain.login

import com.google.firebase.auth.AuthCredential
import com.ramattec.repeater.data.auth.AuthRepository
import com.ramattec.repeater.ui.login.LoginResult
import javax.inject.Inject

class GoogleSigInCredentialUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(firebaseCredential: AuthCredential): LoginResult =
        authRepository.sigInWithGoogleCredential(firebaseCredential)
}