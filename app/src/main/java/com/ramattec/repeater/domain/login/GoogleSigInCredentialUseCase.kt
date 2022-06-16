package com.ramattec.repeater.domain.login

import com.google.firebase.auth.AuthCredential
import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.repository.LoginRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GoogleSigInCredentialUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(firebaseCredential: AuthCredential) = flow {
        emit(Outcome.Progress())
        val loginResult = repository.sigInWithGoogleCredential(firebaseCredential)
        if (loginResult.isSuccess) {
            val user = loginResult.getOrNull()?.let {
                repository.saveNewUserOnFireStore(it).getOrNull()
            }
            emit(Outcome.Success(user))
        }
    }.catch { emit(Outcome.Failure(it)) }
}