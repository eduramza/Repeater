package com.ramattec.repeater.data.model.register

import androidx.annotation.ColorRes
import com.ramattec.repeater.domain.entity.register.UserEntity

data class UserFireStoreModel(
    val firebaseId: String,
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val decks: Deck? = null
) {
    fun toEntity() = UserEntity(
        name,
        email,
        phoneNumber,
        photoUrl
    )
}

data class Deck(
    val deckId: String,
    val name: String,
    val theme: String,
    val description: String?,
    @ColorRes val color: Int
)
