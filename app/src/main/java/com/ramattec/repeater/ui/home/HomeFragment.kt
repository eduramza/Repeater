package com.ramattec.repeater.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentHomeBinding
import com.ramattec.repeater.ui.deck.DeckBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
        setupView()
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
                        homeViewModel.deleteDeck(longPressed)
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
            homeViewModel.getAllDecks()
            homeViewModel.uiState.collect {
                if (it.isLoading) binding.progress.visibility =
                    View.VISIBLE else binding.progress.visibility = View.GONE
                if (it.decksLoaded.isNotEmpty()) adapter.submitList(it.decksLoaded)
                if (it.deckDeleted) homeViewModel.getAllDecks()
            }
        }
    }

    private fun setupView() {
        binding.tvWelcome.text = getString(R.string.welcome_text, homeViewModel.getUsername())
    }

    private fun setupListeners() {
        binding.fabNewDeck.setOnClickListener {
            DeckBottomSheetFragment().show(requireActivity().supportFragmentManager, null)
        }
        binding.imgProfile.setOnClickListener {
//            if (allPermissionsGranted()) {
//
//            } else
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    REQUIRED_PERMISSIONS,
//                    REQUEST_CODE_PERMISSIONS
//                )
        }
    }

//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
//        private const val REQUEST_CODE_PERMISSIONS = 10
//        private val REQUIRED_PERMISSIONS =
//            mutableListOf(
//                Manifest.permission.CAMERA,
//                Manifest.permission.RECORD_AUDIO
//            ).apply {
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
//                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                }
//            }.toTypedArray()
    }

}