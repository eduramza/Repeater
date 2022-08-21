package com.ramattec.domain.use_case.user

import com.ramattec.domain.repository.UserRepository
import javax.inject.Inject

class GetUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getName()
}