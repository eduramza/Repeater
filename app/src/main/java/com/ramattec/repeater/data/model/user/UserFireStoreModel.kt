package com.ramattec.repeater.data.model.user

import android.os.Parcelable
import com.ramattec.repeater.data.model.deck.DeckModel
import com.ramattec.repeater.domain.entity.user.UserEntity
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserFireStoreModel(
    val firebaseId: String,
    val name: String,
    val email: String,
    val phoneNumber: String? = null,
    val photoUrl: String? = null,
    val decks: DeckModel? = null
) : Parcelable {

    constructor() : this("", "", "", null, null, null)

    fun toEntity() = UserEntity(
        name,
        email,
        phoneNumber,
        photoUrl,
        true
    )
}
