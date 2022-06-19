package com.ramattec.repeater.data.repository.register

import com.google.firebase.auth.FirebaseAuth
import com.ramattec.repeater.domain.entity.user.FirebaseUserEntity
import com.ramattec.repeater.domain.repository.RegisterRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class RegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth //data sources
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

}