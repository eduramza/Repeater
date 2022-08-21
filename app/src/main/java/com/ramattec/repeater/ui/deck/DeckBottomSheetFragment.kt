package com.ramattec.repeater.ui.deck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.ramattec.domain.model.deck.Deck
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.BottomSheetDialogDeckBinding
import com.ramattec.repeater.ui.home.HomeEvent
import com.ramattec.repeater.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeckBottomSheetFragment : BottomSheetDialogFragment() {
    private val deckViewModel: DeckViewModel by viewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: BottomSheetDialogDeckBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetDialogDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        BottomSheetDialog(requireContext(), R.style.BottomSheetStyle).apply {
            setCancelable(false)
            setCanceledOnTouchOutside(true)
        }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            deckViewModel.getDeckState().collect {
                when (it) {
                    DeckState.DeckSaved -> {
                        homeViewModel.onEvent(HomeEvent.FetchDecks)
                        dismiss()
                    }
                    DeckState.Error -> showDeckError()
                    DeckState.Loading -> showLoading()
                    else -> {
                        //TODO verify else branch to not used states
                        binding.progress.visibility = View.GONE
                        binding.contentNewDeck.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progress.visibility = View.VISIBLE
        binding.contentNewDeck.visibility = View.GONE
    }

    private fun showDeckError() {
        Snackbar.make(binding.root, getString(R.string.error_saving_deck), LENGTH_SHORT).show()
    }

    private fun setupListeners() {
        binding.btDone.setOnClickListener {
            deckViewModel.onEvent(
                DeckEvent.SavingDeck(
                    Deck(
                        title = binding.titleInput.text.toString(),
                        about = binding.descriptionInput.text.toString(),
                        category = binding.categoryInput.text.toString()
                    )
                )
            )
        }
    }
}