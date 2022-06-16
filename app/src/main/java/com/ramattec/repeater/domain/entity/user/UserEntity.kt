package com.ramattec.repeater.domain.entity.user

data class UserEntity(
    val name: String?,
    val email: String,
    val phone: String?,
    val photo: String?,
    val isLogged: Boolean = false
){
    companion object{
        fun userNotLogged() = UserEntity("", "", "", "", false)
    }
}