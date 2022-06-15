package com.ramattec.repeater.domain.login

import com.ramattec.repeater.data.model.user.UserModel
import com.ramattec.repeater.data.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class EmailPasswordLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<LoginUIState> = flow {
        val result = repository.doLoginWithEmailAndPassword(email, password).getOrNull()
        if (result != null){
            result.apply {
                emit(
                    LoginUIState.Success(
                        UserModel(
                            firebaseId = this.firebaseId,
                            name = this.name, email = this.email, phoneNumber = this.phoneNumber,
                            photoUrl = this.photoUrl
                        )
                    )
                )
            }
        } else {
            emit(LoginUIState.NotLogged)
        }
    }.onStart {
        emit(LoginUIState.Progress)
    }.catch {
        emit(LoginUIState.Failure(it.localizedMessage))
    }
}