package com.ramattec.repeater.domain

import com.ramattec.repeater.domain.entity.user.FirebaseUserEntity
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.entity.user.UserFormEntity

interface RegisterRepository {
    suspend fun doRegisterWithEmailAndPassword(email: String, password:String):
            Result<FirebaseUserEntity>

    suspend fun updateOrCreateUser(uid: String, userFormEntity: UserFormEntity):
            Result<UserEntity>
}