package com.ramattec.repeater.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramattec.repeater.data.model.LoggedInUser
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(
    val dataSource: LoginDataSource,
    val firebaseAuth: FirebaseAuth = Firebase.auth,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    // in-memory cache of the loggedInUser object
    var loggedInUser1: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = loggedInUser1 != null

    private val loginResult = MutableLiveData<Result<LoggedInUser>>()

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        loggedInUser1 = null
    }

    fun logout() {
        loggedInUser1 = null
        dataSource.logout()
    }

    fun login(email: String, password: String): MutableLiveData<Result<LoggedInUser>> {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
            if (task.isSuccessful)
                loginResult.value = (
                    Result.success(
                        LoggedInUser(
                            task.result.user!!.uid,
                            task.result.user!!.displayName ?: "Rogerio"
                        )
                    )
                )
            else
                loginResult.value = (Result.failure(task.exception!!.fillInStackTrace()))
        }
        return loginResult
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.loggedInUser1 = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}