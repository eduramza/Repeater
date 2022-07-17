package com.ramattec.repeater.data.repository.deck

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.repeater.data.model.deck.DeckModel
import com.ramattec.repeater.data.repository.DECK_COLLECTION
import com.ramattec.repeater.data.repository.DECK_SUB_COLLECTION
import com.ramattec.repeater.data.repository.TAG
import com.ramattec.repeater.domain.entity.deck.DeckFormEntity
import com.ramattec.repeater.domain.repository.DeckRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class DeckRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : DeckRepository {

    override suspend fun getAllDecks() =
        suspendCoroutine<Result<List<DeckModel>>> { continuation ->
            fireStore.collection(DECK_COLLECTION)
                .document(firebaseAuth.uid!!)
                .collection(DECK_SUB_COLLECTION)
                .get()
                .addOnSuccessListener {documents ->
                    val results = documents.toObjects(DeckModel::class.java)
                    continuation.resume(Result.success(results))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun saveDeck(deck: DeckFormEntity) =
        suspendCoroutine<Result<Boolean>> { continuation ->
            val deckModel = DeckModel.create(deck)
            fireStore.collection(DECK_COLLECTION)
                .document(firebaseAuth.uid!!)
                .collection(DECK_SUB_COLLECTION)
                .document(deckModel.deckId)
                .set(deckModel)
                .addOnSuccessListener {
                    continuation.resume(Result.success(true))
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun deleteDeck(id: String) =
        suspendCoroutine<Result<Boolean>> { continuation ->
        fireStore.collection(DECK_COLLECTION)
            .document(firebaseAuth.uid!!)
            .collection(DECK_SUB_COLLECTION)
            .document(id)
            .delete()
            .addOnSuccessListener {
                continuation.resume(Result.success(true))
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}