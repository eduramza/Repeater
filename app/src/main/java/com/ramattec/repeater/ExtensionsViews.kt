package com.ramattec.repeater

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.View
import android.widget.Toast

fun View.flipCard(front: View, back: View) {
    try {
        val scale = this.context.resources.displayMetrics.density

        front.cameraDistance = 5000 * scale
        back.cameraDistance = 5000 * scale
        val flipFront = AnimatorInflater.loadAnimator(context, R.animator.flip_front) as AnimatorSet
        val flipBack = AnimatorInflater.loadAnimator(context, R.animator.flip_back) as AnimatorSet

        flipFront.setTarget(front)
        flipBack.setTarget(back)
        flipFront.start()
        flipBack.start()

    } catch (e: Exception) {
        Toast.makeText(
            this.context,
            "Problemas ao tentar usar o flip: ${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}