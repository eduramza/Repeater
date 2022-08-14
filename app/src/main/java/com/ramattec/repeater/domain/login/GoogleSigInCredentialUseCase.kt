package com.ramattec.repeater.domain.login

import com.google.firebase.auth.AuthCredential
import com.ramattec.domain.ResponseResult
import com.ramattec.repeater.domain.repository.LoginRepository
import com.ramattec.repeater.domain.user.SaveUserOnFirebaseUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GoogleSigInCredentialUseCase @Inject constructor(
    private val repository: LoginRepository,
    private val saveUserOnFirebaseUseCase: SaveUserOnFirebaseUseCase
) {
    suspend operator fun invoke(firebaseCredential: AuthCredential) = flow {
        emit(ResponseResult.Progress())
        val loginResult = repository.sigInWithGoogleCredential(firebaseCredential)
        if (loginResult.isSuccess) {
            val user = loginResult.getOrNull()?.let {
                saveUserOnFirebaseUseCase(it)
            }
            emit(ResponseResult.Success(user))
        }
    }.catch { emit(ResponseResult.Failure(it)) }
}