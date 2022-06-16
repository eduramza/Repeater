package com.ramattec.repeater.domain.register

import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.RegisterRepository
import com.ramattec.repeater.domain.entity.user.UserFormEntity
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserEmailAndPasswordUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(userFormEntity: UserFormEntity) = flow {
        emit(Outcome.Progress())
        val result =
            registerRepository.doRegisterWithEmailAndPassword(
                userFormEntity.email, userFormEntity.password)
        if (result.isSuccess){
            val user = result.getOrNull()?.uid?.let {
                registerRepository
                    .updateOrCreateUser(it, userFormEntity)
            }
            emit(Outcome.Success(user?.getOrNull()))
        }
    }.catch { emit(Outcome.Failure(it)) }
}