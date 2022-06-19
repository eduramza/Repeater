package com.ramattec.repeater.data.repository.deck

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.repeater.data.model.deck.DeckModel
import com.ramattec.repeater.data.repository.DECK_COLLECTION
import com.ramattec.repeater.data.repository.DECK_SUB_COLLECTION
import com.ramattec.repeater.data.repository.TAG
import com.ramattec.repeater.domain.repository.DeckRepository
import javax.inject.Inject
import javax.inject.Singleton
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
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, it)
                }
        }

    override suspend fun addNewDeck(deck: DeckModel) =
        suspendCoroutine<Result<List<DeckModel>>> { continuation ->
            fireStore.collection(DECK_COLLECTION)
                .document(firebaseAuth.uid!!)
                .collection(DECK_SUB_COLLECTION)
                .document(deck.deckId)
                .set(deck)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        Log.d(TAG, "deck salvo com sucesso")
                    else
                        Log.w(TAG, it.exception)
                }.addOnFailureListener {
                    Log.w(TAG, it)
                }
        }
}