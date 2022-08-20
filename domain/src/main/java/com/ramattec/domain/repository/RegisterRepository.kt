package com.ramattec.domain.repository

import com.ramattec.domain.NetworkResult
import com.ramattec.domain.model.user.User

interface RegisterRepository {
    suspend fun createNewUser(email: String, password: String): NetworkResult<User>
}