package com.ramattec.repeater.domain.register

import javax.inject.Inject

private const val EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"

class EmailValidateUseCase @Inject constructor() {
    operator fun invoke(email: String) = email.contains(Regex(EMAIL_PATTERN))
}