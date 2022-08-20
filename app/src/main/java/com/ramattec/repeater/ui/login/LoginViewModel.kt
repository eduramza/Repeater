package com.ramattec.repeater.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.ramattec.domain.NetworkResult.*
import com.ramattec.domain.use_case.login.EmailPasswordLoginUseCase
import com.ramattec.domain.use_case.login.GoogleLoginUseCase
import com.ramattec.domain.use_case.login.IsUserLoggedUseCase
import com.ramattec.domain.use_case.register.EmailValidateUseCase
import com.ramattec.domain.use_case.register.PasswordValidateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailPasswordLoginUseCase: EmailPasswordLoginUseCase,
    private val isUserLoggedUseCase: IsUserLoggedUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val emailValidateUseCase: EmailValidateUseCase,
    private val passwordValidateUseCase: PasswordValidateUseCase
) : ViewModel() {

    private val loginStateFlow = MutableStateFlow<LoginState>(LoginState.Initial)
    fun getLoginState(): StateFlow<LoginState> = loginStateFlow

    private var loginJob: Job? = null

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailFilled -> verifyEmail(event.email)
            is LoginEvent.LoginWithEmailAndPassword -> doLoginWithEmailAndPassword(
                event.email,
                event.password
            )
            is LoginEvent.LoginWithGoogleAccount -> doLoginWithGoogle(event.credential)
            is LoginEvent.PasswordFilled -> verifyPassword(event.password)
            is LoginEvent.IsUserLogged -> verifyIfUserIsLogged()
        }
    }

    private fun verifyPassword(password: String) {
        TODO("Not yet implemented")
    }

    private fun verifyEmail(email: String) {
        TODO("Not yet implemented")
    }

    private fun verifyIfUserIsLogged() {
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            isUserLoggedUseCase().collectLatest {
                when (it) {
                    is Failure -> loginStateFlow.value = LoginState.NetworkError
                    is Progress -> loginStateFlow.value = LoginState.Progress
                    is Success -> loginStateFlow.value = LoginState.UserLogged(it.data)
                }
            }
        }
    }

    private fun doLoginWithEmailAndPassword(email: String, password: String) {
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            emailPasswordLoginUseCase(email, password).collectLatest {
                when (it) {
                    is Failure -> loginStateFlow.value = LoginState.NetworkError
                    is Progress -> loginStateFlow.value = LoginState.Progress
                    is Success -> loginStateFlow.value = LoginState.UserLogged(it.data)
                }
            }
        }
    }

    private fun doLoginWithGoogle(firebaseCredential: AuthCredential) {
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            googleLoginUseCase(firebaseCredential).collectLatest {
                when (it) {
                    is Failure -> loginStateFlow.value = LoginState.NetworkError
                    is Progress -> loginStateFlow.value = LoginState.Progress
                    is Success -> loginStateFlow.value = LoginState.UserLogged(it.data)
                }
            }
        }
    }
}