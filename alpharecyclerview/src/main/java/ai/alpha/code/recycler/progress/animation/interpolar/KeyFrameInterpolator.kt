package ai.alpha.code.recycler.progress.animation.interpolar

import android.animation.TimeInterpolator
import android.view.animation.Interpolator

class KeyFrameInterpolator private constructor(
    private val interpolator: TimeInterpolator,
    private vararg var fractions: Float
) :
    Interpolator {
    private fun setFractions(vararg fractions: Float) {
        this.fractions = fractions
    }

    override fun getInterpolation(input: Float): Float {
        var newInput = input
        if (fractions.size > 1) {
            for (i in 0 until fractions.size - 1) {
                val start = fractions[i]
                val end = fractions[i + 1]
                val duration = end - start
                if (newInput in start..end) {
                    newInput = (newInput - start) / duration
                    return start + (interpolator.getInterpolation(newInput)
                            * duration)
                }
            }
        }
        return interpolator.getInterpolation(newInput)
    }

    companion object {
        fun easeInOut(fractions: FloatArray): KeyFrameInterpolator {
            val interpolator = KeyFrameInterpolator(Ease.inOut())
            interpolator.setFractions(*fractions)
            return interpolator
        }
    }

}