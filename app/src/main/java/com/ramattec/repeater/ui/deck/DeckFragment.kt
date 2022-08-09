package com.ramattec.repeater.ui.deck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentDeckBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DeckFragment: Fragment() {

    private var _binding: FragmentDeckBinding? = null
    private val binding get() = _binding!!
        private val deckViewModel: DeckViewModel by viewModels()
    private val args : DeckFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupView()
        setupListeners()
    }

    private fun setupListeners() {
        binding.cardNextAsk.setOnClickListener {
            findNavController().navigate(R.id.action_deckFragment_to_flashCardFragment)
        }
        binding.btStudy.setOnClickListener {
            findNavController().navigate(R.id.action_deckFragment_to_flashCardFragment)
        }
        binding.cardAddItem.setOnClickListener {
            findNavController().navigate(R.id.action_deckFragment_to_editCardFragment)
        }
    }

    private fun setupView() {
        binding.imgSettings.setOnClickListener {
            deckViewModel.deleteDeck(args.deckId)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            deckViewModel.uiState.collect {
                if (it.deleteWithSuccess) findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}