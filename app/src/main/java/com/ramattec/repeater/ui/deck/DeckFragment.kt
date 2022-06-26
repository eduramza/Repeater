package com.ramattec.repeater.ui.deck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentDeckBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DeckFragment: Fragment() {
    private val deckViewModel: DeckViewModel by viewModels()
    private var _binding: FragmentDeckBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            deckViewModel.uiState.collect {
                if (it.isLoading) binding.containerProgress.progress.visibility = View.VISIBLE
                    else binding.containerProgress.progress.visibility = View.GONE
                if (it.errorMessage) showDeckError()
                if (it.success) findNavController().navigate(R.id.action_deckFragment_to_homeFragment)
            }
        }
    }

    private fun showDeckError() {
        Snackbar.make(binding.root, getString(R.string.error_saving_deck), LENGTH_SHORT).show()
    }

    private fun setupListeners() {
        binding.btDone.setOnClickListener { deckViewModel.saveOrUpdateDeck(
            binding.titleInput.text.toString(),
            binding.categoryInput.text.toString(),
            binding.descriptionInput.text.toString()
        ) }
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_deckFragment_to_homeFragment)
        }
    }
}