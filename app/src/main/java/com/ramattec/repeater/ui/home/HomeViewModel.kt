package com.ramattec.repeater.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.repeater.data.repository.TAG
import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.deck.AddNewDeckUseCase
import com.ramattec.repeater.domain.deck.FetchAllUserDecksUseCase
import com.ramattec.repeater.domain.user.GetUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addNewDeckUseCase: AddNewDeckUseCase,
    private val fetchAllUserDecksUseCase: FetchAllUserDecksUseCase,
    private val getUsernameUseCase: GetUsernameUseCase
): ViewModel() {

    fun addNewDeck(){
        viewModelScope.launch {
            addNewDeckUseCase().collect {
                when(it){
                    is Outcome.Success -> Log.d(TAG, "sucesso na viewmodel")
                }
            }
        }
    }

    fun getAllDecks(){
        viewModelScope.launch {
            fetchAllUserDecksUseCase().collect {
                when(it){
                    is Outcome.Success -> Log.d(TAG, "sucesso na viewmodel")
                }
            }
        }
    }

    fun getUsername() = getUsernameUseCase()
}