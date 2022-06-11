package com.ramattec.repeater.di

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Module
@InstallIn(SingletonComponent::class)
object AuthProvider {
    private val auth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ) = suspendCoroutine<FirebaseUser?> { continuation ->
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(result.user)
                } ?: run {
                    continuation.resume(null)
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    suspend fun loginWithGoogleCredentials(
        firebaseCredential: AuthCredential
    ) = suspendCoroutine<FirebaseUser?> { continuation ->
        auth.signInWithCredential(firebaseCredential)
            .addOnSuccessListener { result ->
                result.user?.let {
                    continuation.resume(result.user)
                } ?: run {
                    continuation.resume(null)
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }
}