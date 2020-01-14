package ai.alpha.code.recycler.progress.animation.interpolar

import android.view.animation.Interpolator

internal object Ease {
    fun inOut(): Interpolator {
        return PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f)
    }
}