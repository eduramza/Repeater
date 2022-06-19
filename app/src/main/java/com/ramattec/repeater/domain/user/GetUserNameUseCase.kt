package com.ramattec.repeater.domain.user

import com.ramattec.repeater.domain.repository.UserRepository
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUserName()
}