package com.ramattec.repeater.domain

import com.ramattec.repeater.domain.entity.register.FirebaseUserEntity
import com.ramattec.repeater.domain.entity.register.UserEntity
import com.ramattec.repeater.domain.entity.register.UserFormEntity

interface RegisterRepository {
    suspend fun doRegisterWithEmailAndPassword(email: String, password:String):
            Result<FirebaseUserEntity>

    suspend fun updateOrCreateUser(uid: String, userFormEntity: UserFormEntity):
            Result<UserEntity>
}