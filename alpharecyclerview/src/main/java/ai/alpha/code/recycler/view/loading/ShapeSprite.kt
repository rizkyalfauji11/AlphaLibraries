package ai.alpha.code.recycler.view.loading

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint

abstract class ShapeSprite internal constructor() : Sprite() {
    private val mPaint: Paint
    private var mUseColor = 0
    private var mBaseColor = 0

    final override var color: Int
        get() = mBaseColor
        set(color) {
            mBaseColor = color
            updateUseColor()
        }

    override fun setAlpha(alpha: Int) {
        super.setAlpha(alpha)
        updateUseColor()
    }

    private fun updateUseColor() {
        var alpha = alpha
        alpha += alpha shr 7
        val baseAlpha = mBaseColor ushr 24
        val useAlpha = baseAlpha * alpha shr 8
        mUseColor = mBaseColor shl 8 ushr 8 or (useAlpha shl 24)
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun drawSelf(canvas: Canvas?) {
        mPaint.color = mUseColor
        drawShape(canvas, mPaint)
    }

    protected abstract fun drawShape(
        canvas: Canvas?,
        paint: Paint?
    )

    init {
        color = Color.WHITE
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = mUseColor
    }
}