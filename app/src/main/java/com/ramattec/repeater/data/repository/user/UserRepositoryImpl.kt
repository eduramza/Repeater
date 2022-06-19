package com.ramattec.repeater.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.repeater.data.MyPreferenceManager
import com.ramattec.repeater.data.model.user.UserFireStoreModel
import com.ramattec.repeater.data.repository.USER_COLLECTION
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.entity.user.UserFormEntity
import com.ramattec.repeater.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val pref: MyPreferenceManager
) : UserRepository{

    override fun getUserName() = pref.getStoredName()

    override suspend fun updateUserOrCreate(userFormEntity: UserFormEntity) =
        suspendCoroutine<Result<UserEntity>> { continuation ->
            val body = createNewUser(firebaseAuth.uid!!, userFormEntity)
            pref.setStoredName(userFormEntity.name)
            fireStore.collection(USER_COLLECTION)
                .document(firebaseAuth.uid!!)
                .set(body)
                .addOnSuccessListener {
                    continuation.resume(
                        Result.success(
                            body.toEntity()
                        )
                    )
                }
                .addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }

    private fun createNewUser(uid: String, user: UserFormEntity) =
        UserFireStoreModel(
            firebaseId = uid,
            name = user.name,
            email = user.email,
            phoneNumber = user.phoneNumber,
            photoUrl = user.photoUrl
        )
}