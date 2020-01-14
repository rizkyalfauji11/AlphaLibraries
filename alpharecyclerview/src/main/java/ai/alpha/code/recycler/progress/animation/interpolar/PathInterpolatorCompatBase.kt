package ai.alpha.code.recycler.progress.animation.interpolar

import android.graphics.Path
import android.view.animation.Interpolator

internal object PathInterpolatorCompatBase {
    fun create(path: Path?): Interpolator {
        return PathInterpolatorDonut(path)
    }

    fun create(
        controlX: Float,
        controlY: Float
    ): Interpolator {
        return PathInterpolatorDonut(controlX, controlY)
    }

    fun create(
        controlX1: Float, controlY1: Float,
        controlX2: Float, controlY2: Float
    ): Interpolator {
        return PathInterpolatorDonut(controlX1, controlY1, controlX2, controlY2)
    }
}