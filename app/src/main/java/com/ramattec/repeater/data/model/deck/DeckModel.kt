package com.ramattec.repeater.data.model.deck

import android.os.Parcelable
import androidx.annotation.ColorRes
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import com.ramattec.repeater.domain.entity.deck.DeckFormEntity
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class DeckModel(
    val deckId: String,
    val title: String,
    val category: String,
    val description: String?,
    @ServerTimestamp
    @get: PropertyName("createdAt") @set: PropertyName("createdAt") var timestamp: Date= Date()
) : Parcelable{
    constructor(): this("", "", "", null, Date())

    companion object {
        fun create(entity: DeckFormEntity): DeckModel {
            return DeckModel(
                deckId = UUID.randomUUID().toString(),
                title = entity.title,
                category = entity.category ?: "",
                description = entity.description
            ).apply {
                this.timestamp to FieldValue.serverTimestamp()
            }
        }
    }
}