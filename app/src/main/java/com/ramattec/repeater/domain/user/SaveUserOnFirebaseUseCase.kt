package com.ramattec.repeater.domain.user

import com.ramattec.repeater.domain.entity.user.UserFormEntity
import com.ramattec.repeater.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserOnFirebaseUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userFormEntity: UserFormEntity) =
        userRepository.updateUserOrCreate(userFormEntity).getOrNull()
}