package com.ramattec.data.model.deck

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DeckDto(
    val deckId: String,
    val title: String,
    val about: String,
    val category: String,
    @ServerTimestamp
    @get: PropertyName("createdAt") @set: PropertyName("createdAt") var timestamp: Date = Date()
) : Parcelable {
    constructor() : this("", "", "", "", Date())
}