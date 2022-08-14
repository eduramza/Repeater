package com.ramattec.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.data.mapper.toDeck
import com.ramattec.data.mapper.toDeckDto
import com.ramattec.data.model.deck.DeckDto
import com.ramattec.domain.ResponseResult
import com.ramattec.domain.model.deck.Deck
import com.ramattec.domain.repository.DeckRepository
import com.ramattec.repeater.data.repository.DECK_COLLECTION
import com.ramattec.repeater.data.repository.DECK_SUB_COLLECTION
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class DeckRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : DeckRepository {


    override suspend fun fetchDecks(): ResponseResult<List<Deck>> =
        suspendCoroutine { continuation ->
            firestore.collection(DECK_COLLECTION)
                .document(auth.uid!!)
                .collection(DECK_SUB_COLLECTION)
                .get()
                .addOnSuccessListener { docs ->
                    val results = docs.toObjects(DeckDto::class.java)
                    continuation.resume(ResponseResult.Success(results.map { it.toDeck() }))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }

        }

    override suspend fun saveDeck(deck: Deck): ResponseResult<Boolean> =
        suspendCoroutine { continuation ->
            val dto = deck.toDeckDto()
            firestore.collection(DECK_COLLECTION)
                .document(auth.uid!!)
                .collection(DECK_SUB_COLLECTION)
                .document(dto.deckId)
                .set(dto)
                .addOnSuccessListener {
                    continuation.resume(ResponseResult.Success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun deleteDeck(id: String): ResponseResult<Boolean> =
        suspendCoroutine { continuation ->
            firestore.collection(DECK_COLLECTION)
                .document(auth.uid!!)
                .collection(DECK_SUB_COLLECTION)
                .document(id)
                .delete()
                .addOnSuccessListener {
                    continuation.resume(ResponseResult.Success(true))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
}