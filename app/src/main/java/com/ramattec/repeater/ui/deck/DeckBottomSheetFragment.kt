package com.ramattec.repeater.ui.deck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.BottomSheetDialogDeckBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DeckBottomSheetFragment : BottomSheetDialogFragment() {
    private val deckViewModel: DeckViewModel by viewModels()
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
            deckViewModel.uiState.collect {
                if (it.isLoading) {
                    binding.progress.visibility = View.VISIBLE
                    binding.contentNewDeck.visibility = View.INVISIBLE
                } else {
                    binding.progress.visibility = View.GONE
                    binding.contentNewDeck.visibility = View.VISIBLE
                }
                if (it.errorMessage) showDeckError()
                if (it.saveWithSuccess) { dismiss() }
            }
        }
    }

    private fun showDeckError() {
        Snackbar.make(binding.root, getString(R.string.error_saving_deck), LENGTH_SHORT).show()
    }

    private fun setupListeners() {
        binding.btDone.setOnClickListener {
            deckViewModel.saveOrUpdateDeck(
                binding.titleInput.text.toString(),
                binding.categoryInput.text.toString(),
                binding.descriptionInput.text.toString()
            )
        }
    }
}