package com.ramattec.repeater.domain.repository

import com.ramattec.repeater.domain.entity.user.FirebaseUserEntity
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.entity.user.UserFormEntity

interface RegisterRepository {
    suspend fun doRegisterWithEmailAndPassword(email: String, password:String):
            Result<FirebaseUserEntity>
}