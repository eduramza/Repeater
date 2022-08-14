package com.ramattec.repeater.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.domain.ResponseResult
import com.ramattec.repeater.domain.entity.user.UserFormEntity
import com.ramattec.repeater.domain.register.RegisterUserEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserEmailAndPasswordUseCase: RegisterUserEmailAndPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState = _uiState.asStateFlow()

    private var registerJob: Job? = null

    fun doRegister(email: String, password: String, name: String) {
        //TODO validate all fields
        registerJob?.cancel()
        registerJob = viewModelScope.launch {
            registerUserEmailAndPasswordUseCase(
                UserFormEntity(email = email, password = password, name = name)
            ).collect { result ->
                when (result) {
                    is ResponseResult.Progress -> _uiState.value = RegisterUIState(isLoading = true)
                    is ResponseResult.Failure -> _uiState.value =
                        RegisterUIState(errorMessage = result.e.localizedMessage!!)
                    is ResponseResult.Success -> _uiState.value = RegisterUIState(newUser = result.data)
                }
            }
        }
    }

}