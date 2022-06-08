package com.ramattec.repeater.data

import com.ramattec.repeater.data.model.LoggedInUser
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): RepeaterResult<LoggedInUser> {
        return try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "Jane Doe")
            RepeaterResult.Success(fakeUser)
        } catch (e: Throwable) {
            RepeaterResult.Error("Error logging in")
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}