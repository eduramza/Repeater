package com.ramattec.repeater.ui.deck

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
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
    }

    private fun setupView() {
        binding.imgConfig.setOnClickListener {
//            deckViewModel.deleteDeck(args.deckId)
        }
        binding.containerAsk.root.setOnClickListener {
            flipCard(binding.containerAnswer.root, it)
        }
        binding.containerAnswer.root.setOnClickListener {
            flipCard(binding.containerAsk.root, it)
        }
    }

    private fun flipCard(visibleView: View, inVisibleView: View) {
        try {
            visibleView.visibility = VISIBLE
            val scale = requireContext().resources.displayMetrics.density
            val cameraDist = 8000 * scale
            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist
            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet
            flipOutAnimatorSet.setTarget(inVisibleView)
            val flipInAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet
            flipInAnimatorSet.setTarget(visibleView)
            flipOutAnimatorSet.start()
            flipInAnimatorSet.start()
            flipInAnimatorSet.doOnEnd {
                inVisibleView.visibility = GONE
            }
        } catch (e: Exception) {
            //TODO
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