package com.ramattec.repeater.ui.card

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(CardUIState())
    val uiState = _uiState
    private var frontText: String = ""
    private var backText: String = ""

    fun onFrontEdit(text: CharSequence) {
        frontText = text.toString()
        verifyIsButtonEnable()
    }

    fun onBackEdit(text: CharSequence) {
        backText = text.toString()
        verifyIsButtonEnable()
    }

    private fun verifyIsButtonEnable() =
        if (frontText.isNotBlank().and(backText.isNotBlank()))
            _uiState.update {
                CardUIState(
                    isSaveButtonEnable = true,
                    frontItem = frontText,
                    backItem = backText
                )
            }
        else _uiState.update { CardUIState(isSaveButtonEnable = false) }

    fun saveOrUpdateCard(ask: String, answer: String){

    }

}