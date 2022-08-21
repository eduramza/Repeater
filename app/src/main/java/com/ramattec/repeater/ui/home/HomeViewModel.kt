package com.ramattec.repeater.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.domain.NetworkResult
import com.ramattec.domain.model.user.User
import com.ramattec.domain.use_case.deck.DeleteDeckUseCase
import com.ramattec.domain.use_case.deck.FetchDecksUseCase
import com.ramattec.domain.use_case.user.GetUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchAllUserDecksUseCase: FetchDecksUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase
) : ViewModel() {

    private val homeState = MutableStateFlow<HomeState>(HomeState.Initial)
    fun getHomeState(): StateFlow<HomeState> = homeState

    init {
        getAllDecks()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.DeletingDeck -> deleteDeck(event.id)
        }
    }

    private fun getAllDecks() {
       viewModelScope.launch {
            fetchAllUserDecksUseCase().collectLatest {
                when (it) {
                    is NetworkResult.Failure -> homeState.value = HomeState.ErrorFetchDeck
                    is NetworkResult.Progress -> homeState.value = HomeState.Loading
                    is NetworkResult.Success -> {
                        if (it.data.isEmpty()) homeState.value = HomeState.EmptyDeckList
                        else {
                            homeState.value = HomeState.DecksFetched(it.data)
                            getUsername()
                        }
                    }
                }
            }
        }
    }

    private fun getUsername() {
        //TODO get data of user like photo
        homeState.value = HomeState.UserLoaded(User(name = getUsernameUseCase()))
    }

    private fun deleteDeck(id: String) {
        viewModelScope.launch {
            deleteDeckUseCase(id).collectLatest {
                when (it) {
                    is NetworkResult.Failure -> homeState.value = HomeState.ErrorDeleteDeck
                    is NetworkResult.Progress -> homeState.value = HomeState.Loading
                    is NetworkResult.Success -> getAllDecks()
                }
            }
        }
    }
}