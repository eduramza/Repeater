package com.ramattec.repeater.domain.login

import com.google.firebase.auth.FirebaseAuth
import com.ramattec.repeater.R
import com.ramattec.repeater.ui.login.LoggedUserView
import com.ramattec.repeater.ui.login.LoginResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
class EmailPasswordLoginUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    operator fun invoke(email: String, password: String): Flow<LoginResult> = channelFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                GlobalScope.launch(Dispatchers.IO) {
                    if (task.isSuccessful)
                        send(
                            LoginResult(
                                success = LoggedUserView(
                                    displayName =
                                    firebaseAuth.currentUser?.displayName ?: "Paulino"
                                )
                            )
                        )
                    else
                        send(LoginResult(error = R.string.login_failed))
                }
            }
    }
}