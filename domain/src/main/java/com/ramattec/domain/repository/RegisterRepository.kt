package com.ramattec.domain.repository

import com.ramattec.domain.ResponseResult
import com.ramattec.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun createNewUser(email: String, password: String): ResponseResult<User>
}