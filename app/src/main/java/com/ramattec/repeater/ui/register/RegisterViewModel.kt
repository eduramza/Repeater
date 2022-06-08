package com.ramattec.repeater.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _registerResult = MutableLiveData<UIState>()
    val registerResult: LiveData<UIState> = _registerResult

    fun doRegister(email: String, password: String, name: String){
        //TODO validate all fields
        viewModelScope.launch {
            _registerResult.postValue(UIState(isLoading = true))
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        updateUser(name)
                    else
                        _registerResult.postValue(UIState(error = "Deu ruim pae"))
                }
        }
    }

    private fun updateUser(name: String){
        val profileUpdates = userProfileChangeRequest { displayName = name }
        firebaseAuth.currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful)
                    _registerResult.postValue(UIState(success = UserRegistered(name)))
            }
    }

}