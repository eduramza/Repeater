package com.ramattec.repeater.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramattec.domain.model.user.User
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentHomeBinding
import com.ramattec.repeater.ui.deck.DeckBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
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
        setupListeners()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = DecksAdapter(
            { id ->
                val action = HomeFragmentDirections.actionHomeFragmentToDeckFragment(id)
                findNavController().navigate(action)
            }, { longPressed ->
                val builder = AlertDialog.Builder(requireContext())
                    .setMessage(R.string.wish_exclude_item)
                    .setPositiveButton(R.string.delete) { _, _ ->
                        homeViewModel.onEvent(
                            HomeEvent.DeletingDeck(longPressed)
                        )
                    }
                    .setNegativeButton(R.string.cancel) { _, _ -> }
                builder.create()
                builder.show()
            })
        binding.rvDecks.adapter = adapter
        binding.rvDecks.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            homeViewModel.getHomeState().collect {
                when (it) {
                    is HomeState.DecksFetched -> {
                        binding.rvDecks.visibility = VISIBLE
                        binding.progress.visibility = GONE
                        adapter.submitList(it.decks)
                    }
                    HomeState.EmptyDeckList -> showEmptyDeckView()
                    HomeState.ErrorDeleteDeck -> TODO()
                    HomeState.ErrorFetchDeck -> showDeckFetchError()
                    HomeState.Loading -> showLoading()
                    is HomeState.UserLoaded -> showUserData(it.user)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progress.visibility = VISIBLE
        binding.rvDecks.visibility = GONE
    }

    private fun showDeckFetchError() {
        binding.progress.visibility = GONE
    }

    private fun showEmptyDeckView() {
        binding.progress.visibility = GONE
    }

    private fun showUserData(user: User) {
        binding.tvWelcome.text =
            getString(
                R.string.welcome_text,
                user.name
            )
    }

    private fun setupListeners() {
        binding.fabNewDeck.setOnClickListener {
            DeckBottomSheetFragment().show(requireActivity().supportFragmentManager, null)
        }
        binding.imgProfile.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}