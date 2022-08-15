package com.ramattec.domain.use_case.register

import javax.inject.Inject

private const val PASSWORD_REGEX = """^(?=.*[0-9])(?=.*[a-z,A-Z]).{6,}"""

class PasswordValidateUseCase @Inject constructor() {
    operator fun invoke(password: String) =
        password.contains(Regex(PASSWORD_REGEX))
}