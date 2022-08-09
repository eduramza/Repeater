package com.ramattec.repeater.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ramattec.repeater.databinding.FragmentEditCardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditCardFragment : Fragment() {

    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!
    private val cardViewModel: CardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            cardViewModel.uiState.collectLatest {
                binding.btSaveCard.isEnabled = it.isSaveButtonEnable
            }
        }
    }

    private fun setupListeners() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.etAsk.doOnTextChanged { text, _, _, _ ->
            text?.let { cardViewModel.onFrontEdit(it) }
        }
        binding.etBack.doOnTextChanged { text, _, _, _ ->
            text?.let { cardViewModel.onBackEdit(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}