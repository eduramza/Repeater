package com.ramattec.repeater.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.domain.NetworkResult
import com.ramattec.domain.model.user.User
import com.ramattec.domain.use_case.register.SignupWithEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signupWithEmailAndPasswordUseCase: SignupWithEmailAndPasswordUseCase
) : ViewModel() {

    private val registerStateFlow = MutableStateFlow<RegisterState>(RegisterState.Initial)
    fun getRegisterStateFlow(): StateFlow<RegisterState> = registerStateFlow

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.RegisterNewUser -> {
                viewModelScope.launch {
                    doRegister(event.data)
                }
            }
        }
    }

    private fun doRegister(user: User) {
        //TODO validate all fields
        viewModelScope.launch {
            signupWithEmailAndPasswordUseCase(user).collectLatest {
                when (it) {
                    is NetworkResult.Progress -> registerStateFlow.value = RegisterState.Loading
                    is NetworkResult.Failure -> registerStateFlow.value = RegisterState.NetworkError
                    is NetworkResult.Success -> registerStateFlow.value =
                        RegisterState.Success(it.data)
                }
            }
        }
    }
}