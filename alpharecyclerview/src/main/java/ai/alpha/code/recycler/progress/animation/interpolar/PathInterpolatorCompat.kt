package ai.alpha.code.recycler.progress.animation.interpolar

import android.os.Build
import android.view.animation.Interpolator

internal object PathInterpolatorCompat {
    fun create(
        controlX1: Float, controlY1: Float,
        controlX2: Float, controlY2: Float
    ): Interpolator {
        return if (Build.VERSION.SDK_INT >= 21) {
            PathInterpolatorCompatApi21.create(controlX1, controlY1, controlX2, controlY2)
        } else PathInterpolatorCompatBase.create(controlX1, controlY1, controlX2, controlY2)
    }
}
