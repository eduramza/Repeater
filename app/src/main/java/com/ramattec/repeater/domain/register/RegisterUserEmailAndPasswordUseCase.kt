package com.ramattec.repeater.domain.register

import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.entity.user.UserFormEntity
import com.ramattec.repeater.domain.repository.RegisterRepository
import com.ramattec.repeater.domain.user.SaveUserOnFirebaseUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserEmailAndPasswordUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val saveUserOnFirebaseUseCase: SaveUserOnFirebaseUseCase
) {
    suspend operator fun invoke(userFormEntity: UserFormEntity) = flow {
        emit(Outcome.Progress())
        val result =
            registerRepository.doRegisterWithEmailAndPassword(
                userFormEntity.email, userFormEntity.password)
        if (result.isSuccess){
            val user = result.getOrNull()?.uid?.let {
                saveUserOnFirebaseUseCase(userFormEntity)
            }
            emit(Outcome.Success(user))
        }
    }.catch { emit(Outcome.Failure(it)) }
}