package com.ramattec.repeater.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentHomeBinding
import com.ramattec.repeater.ui.deck.DeckBottomSheetFragment
import com.ramattec.repeater.ui.deck.EXTRA_NEW_DECK
import com.ramattec.repeater.ui.deck.KEY_NEW_DECK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: DecksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListeners()
        setupRecyclerView()
        setupObservers()
        requireActivity().supportFragmentManager.setFragmentResultListener(
            KEY_NEW_DECK, this){_, bundle ->
            if (bundle.getBoolean(EXTRA_NEW_DECK))
                homeViewModel.getAllDecks()
        }
    }

    private fun setupRecyclerView() {
        adapter = DecksAdapter() { id ->
            Snackbar.make(binding.root, "Clicked on Item : $id", Snackbar.LENGTH_SHORT).show()
        }
        binding.rvDecks.adapter = adapter
        binding.rvDecks.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setupObservers() {
        homeViewModel.getAllDecks()
        lifecycleScope.launchWhenCreated {
            homeViewModel.uiState.collect {
                if (it.isLoading) binding.progress.visibility =
                    View.VISIBLE else binding.progress.visibility = View.GONE
                if (it.decksLoaded.isNotEmpty()) adapter.submitList(it.decksLoaded)
            }
        }
    }

    private fun setupView() {
        binding.tvWelcome.text = getString(R.string.welcome_text, homeViewModel.getUsername())
    }

    private fun setupListeners() {
        binding.fabNewDeck.setOnClickListener {
            DeckBottomSheetFragment().show(requireActivity().supportFragmentManager, KEY_NEW_DECK)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}