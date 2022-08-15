package com.ramattec.domain.use_case.user

import com.ramattec.domain.model.user.User
import com.ramattec.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) = userRepository.updateUser(user)
}