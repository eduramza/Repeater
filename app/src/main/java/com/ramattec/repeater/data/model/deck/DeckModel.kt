package com.ramattec.repeater.data.model.deck

import android.os.Parcelable
import androidx.annotation.ColorRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeckModel(
    val deckId: String,
    val name: String,
    val theme: String,
    val description: String?,
    val color: Int = 1
) : Parcelable{
    constructor(): this("", "", "", null, 1)
}