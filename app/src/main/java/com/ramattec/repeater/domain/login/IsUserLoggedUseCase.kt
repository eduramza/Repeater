package com.ramattec.repeater.domain.login

import com.ramattec.repeater.data.auth.AuthRepository
import com.ramattec.repeater.ui.login.LoggedUserView
import com.ramattec.repeater.ui.login.LoginResult
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() =
        if (repository.verifyUserLogged())
            LoginResult(
                success = LoggedUserView(displayName = "Pingolino")
            )
        else
            LoginResult()
}
