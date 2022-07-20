package com.ramattec.repeater.ui.game

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ramattec.repeater.R
import com.ramattec.repeater.databinding.FragmentFlashCardBinding

class FlashCardFragment : Fragment() {
    private var _binding: FragmentFlashCardBinding? = null
    private val binding get() = _binding!!

    private val flipFront by lazy {
        AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.flip_front
        ) as AnimatorSet
    }
    private val flipBack by lazy {
        AnimatorInflater.loadAnimator(
            requireContext(),
            R.animator.flip_back
        ) as AnimatorSet
    }
    var isFront = true

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
        binding.containerAsk.setOnClickListener {
            flipCard()
        }
        binding.containerAnswer.setOnClickListener {
            flipCard()
        }
    }

    private fun flipCard() {
        try {
            val scale = requireContext().resources.displayMetrics.density

            binding.containerAsk.cameraDistance = 8000 * scale
            binding.containerAnswer.cameraDistance = 8000 * scale

            isFront = if (isFront) {
                flipFront.setTarget(binding.containerAsk)
                flipBack.setTarget(binding.containerAnswer)
                false
            } else {
                flipFront.setTarget(binding.containerAnswer)
                flipBack.setTarget(binding.containerAsk)
                true
            }
            flipFront.start()
            flipBack.start()
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Problemas ao tentar usar o flip: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}