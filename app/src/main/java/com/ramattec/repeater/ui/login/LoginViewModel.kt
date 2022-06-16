package com.ramattec.repeater.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.ramattec.repeater.domain.Outcome.*
import com.ramattec.repeater.domain.login.EmailPasswordLoginUseCase
import com.ramattec.repeater.domain.login.GoogleSigInCredentialUseCase
import com.ramattec.repeater.domain.login.IsUserLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailPasswordLoginUseCase: EmailPasswordLoginUseCase,
    private val isUserLoggedUseCase: IsUserLoggedUseCase,
    private val googleSigInCredentialUseCase: GoogleSigInCredentialUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState = _uiState.asStateFlow()

    fun verifyIfUserIsLogged() {
        viewModelScope.launch {
            isUserLoggedUseCase().collect {
                when (it) {
                    is Progress -> _uiState.value = LoginUIState(isLoadingInitialUser = true)
                    is Failure -> _uiState.value = LoginUIState(isLoadingInitialUser = false)
                    is Success -> _uiState.value = LoginUIState(loggedUser = it.data, isLoadingInitialUser = false)
                }
            }
        }
    }

    fun doLoginWithEmailAndPassword(email: String, password: String) {
        if (validateUserInput(email, password)) return
        viewModelScope.launch {
            emailPasswordLoginUseCase(email, password).collect {
                when (it) {
                    is Progress -> _uiState.value = LoginUIState(isLoadingUser = true)
                    is Failure -> _uiState.value = LoginUIState(loginError = it.e.localizedMessage)
                    is Success -> _uiState.value = LoginUIState(loggedUser = it.data)
                }
            }
        }
    }

    private fun validateUserInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            _uiState.value = LoginUIState(isEmailInputInvalid = true)
            return true
        } else if (password.isEmpty()) {
            _uiState.value = LoginUIState(isPasswordInputInvalid = true)
            return true
        }
        return false
    }

    fun doLoginWithGoogle(firebaseCredential: AuthCredential) {
        viewModelScope.launch {
            googleSigInCredentialUseCase(firebaseCredential).collect {
                when (it) {
                    is Progress -> _uiState.value = LoginUIState(isLoadingInitialUser = true)
                    is Failure -> _uiState.value = LoginUIState(loginError = it.e.localizedMessage)
                    is Success -> _uiState.value = LoginUIState(loggedUser = it.data)
                }
            }
        }
    }
}