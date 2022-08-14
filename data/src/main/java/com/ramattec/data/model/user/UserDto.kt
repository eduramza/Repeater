package com.ramattec.data.model.user

import android.os.Parcelable
import com.ramattec.data.model.deck.DeckDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDto(
    val firebaseId: String,
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val decks: DeckDto? = null
) : Parcelable{
    constructor() : this("", "", "", null, null, null)
}