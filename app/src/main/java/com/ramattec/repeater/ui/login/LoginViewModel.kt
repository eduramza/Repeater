package com.ramattec.repeater.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.ramattec.repeater.domain.login.EmailPasswordLoginUseCase
import com.ramattec.repeater.domain.login.GoogleSigInCredentialUseCase
import com.ramattec.repeater.domain.login.IsUserLoggedUseCase
import com.ramattec.repeater.domain.login.LoginUIState
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

    private val _loginResult = MutableLiveData<LoginUIState>()
    val loginResult: LiveData<LoginUIState> = _loginResult

    private val _isLoadingUser = MutableStateFlow(true)
    val isLoadingUser = _isLoadingUser.asStateFlow()

    init {
        verifyIfUserIsLogged()
    }

    private fun verifyIfUserIsLogged() {
        viewModelScope.launch {
             isUserLoggedUseCase().collect {
                 _loginResult.value = it
             }
            _isLoadingUser.value = false
        }
    }

    fun doLoginWithEmailAndPassword(email: String, password: String) {
        //TODO Veirify if email and password is not null string
        viewModelScope.launch {
            _loginResult.value = emailPasswordLoginUseCase(email, password)!!
        }
    }

    fun doLoginWithGoogle(firebaseCredential: AuthCredential) {
        viewModelScope.launch {
            _loginResult.value = googleSigInCredentialUseCase(firebaseCredential)!!
        }
    }
}