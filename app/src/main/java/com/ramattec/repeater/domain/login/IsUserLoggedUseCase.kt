package com.ramattec.repeater.domain.login

import com.ramattec.repeater.data.repository.login.LoginRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke() = flow {
        val user = repository.verifyUserLogged().getOrNull()
        if (user != null) emit(LoginUIState.Success(user))
        else emit(LoginUIState.NotLogged)
    }.onStart {
        emit(LoginUIState.Progress)
    }.catch {
        emit(LoginUIState.Failure(it.localizedMessage))
    }

}
