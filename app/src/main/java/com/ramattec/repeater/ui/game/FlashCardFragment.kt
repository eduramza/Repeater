package com.ramattec.repeater.ui.game

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentFlashCardBinding

class FlashCardFragment: Fragment() {
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

    private fun setupView(){
        binding.containerAsk.root.setOnClickListener {
            flipCard(binding.containerAnswer.root, it)
        }
        binding.containerAnswer.root.setOnClickListener {
            flipCard(binding.containerAsk.root, it)
        }
    }

    private fun flipCard(visibleView: View, inVisibleView: View) {
        try {
            visibleView.visibility = View.VISIBLE
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
                inVisibleView.visibility = View.GONE
            }
        } catch (e: Exception) {
            //TODO
        }
    }
}