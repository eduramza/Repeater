package com.ramattec.repeater.domain.repository

import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.entity.user.UserFormEntity

interface UserRepository {
    fun getUserName(): String
    suspend fun updateUserOrCreate(userFormEntity: UserFormEntity): Result<UserEntity>
}