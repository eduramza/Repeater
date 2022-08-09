package com.ramattec.repeater.ui.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ramattec.repeater.databinding.FragmentFlashCardBinding
import com.ramattec.repeater.flipCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlashCardFragment : Fragment() {
    private var _binding: FragmentFlashCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlashCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.containerAsk.setOnClickListener { it.flipCard(it, binding.containerAnswer) }
        binding.containerAnswer.setOnClickListener { it.flipCard(it, binding.containerAsk) }
        binding.imgBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}