package ai.alpha.code.recycler.view.loading

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.min

open class CircleSprite : ShapeSprite() {
    override fun onCreateAnimation(): ValueAnimator? {
        return null
    }

    override fun drawShape(canvas: Canvas?, paint: Paint?) {
        val radius =
            min(getDrawBounds().width(), getDrawBounds().height()) / 2
        canvas?.drawCircle(
            getDrawBounds().centerX().toFloat(),
            getDrawBounds().centerY().toFloat(),
            radius.toFloat(), paint!!
        )
    }
}