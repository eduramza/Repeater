package com.ramattec.repeater.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramattec.repeater.R
import com.ramattec.repeater.domain.login.EmailPasswordLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _isLoadingUser = MutableStateFlow(true)
    val isLoadingUser = _isLoadingUser.asStateFlow()

    init {
        verifyIfUserIsLogged()
    }

    private fun verifyIfUserIsLogged() {
        viewModelScope.launch {
            if (firebaseAuth.currentUser != null)
                _loginResult.value =
                    LoginResult(
                        success = LoggedUserView(displayName = "Pingolino")
                    )
            _isLoadingUser.value = false
        }
    }

    fun doLogin(email: String, password: String) {
        //TODO Veirify if email and password is not null string
        viewModelScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        _loginResult.value =
                            LoginResult(
                                success = LoggedUserView(
                                    displayName =
                                    firebaseAuth.currentUser?.displayName ?: "Paulino"
                                )
                            )
                    else
                        _loginResult.value = LoginResult(error = R.string.login_failed)
                }
        }
    }
}