package com.ramattec.repeater.data.repository.register

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ramattec.repeater.data.model.user.UserFireStoreModel
import com.ramattec.repeater.data.repository.USER_COLLECTION
import com.ramattec.repeater.domain.RegisterRepository
import com.ramattec.repeater.domain.entity.user.FirebaseUserEntity
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.entity.user.UserFormEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class RegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth, //data sources
    private val fireStore: FirebaseFirestore //data sources
) : RegisterRepository {

    override suspend fun doRegisterWithEmailAndPassword(email: String, password: String) =
        suspendCoroutine<Result<FirebaseUserEntity>> { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    if (it.user != null)
                        continuation.resume(Result.success(FirebaseUserEntity(it.user!!.uid)))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun updateOrCreateUser(uid: String, userFormEntity: UserFormEntity) =
        suspendCoroutine<Result<UserEntity>> { continuation ->
            val body = createNewUser(uid, userFormEntity)
            fireStore.collection(USER_COLLECTION)
                .add(body)
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