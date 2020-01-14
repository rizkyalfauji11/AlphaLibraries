package ai.alpha.code.recycler.view.loading

import ai.alpha.code.recycler.progress.animation.AnimationUtils
import ai.alpha.code.recycler.progress.animation.FloatProperty
import ai.alpha.code.recycler.progress.animation.IntProperty
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.Property


abstract class Sprite internal constructor() : Drawable(), ValueAnimator.AnimatorUpdateListener,
    Animatable,
    Drawable.Callback {
    private var scale = 1f
    private var scaleX = 1f
    private var scaleY = 1f
    private var pivotX = 0f
    private var pivotY = 0f
    private var animationDelay = 0
    private var rotateX = 0
    private var rotateY = 0
    private var translateX = 0
    private var translateY = 0
    private var rotate = 0
    private var translateXPercentage = 0f
    private var translateYPercentage = 0f
    private var animator: ValueAnimator? = null
    private var alpha = 255
    private var drawBounds =
        ZERO_BOUNDS_RECT
    private val mCamera: Camera = Camera()
    private val mMatrix: Matrix = Matrix()
    abstract var color: Int
    override fun setAlpha(alpha: Int) {
        this.alpha = alpha
    }

    override fun getAlpha(): Int {
        return alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    private fun getScale(): Float {
        return scale
    }

    fun setScale(scale: Float) {
        this.scale = scale
        scaleX = scale
        scaleY = scale
    }

    fun setAnimationDelay(animationDelay: Int) {
        this.animationDelay = animationDelay
    }


    override fun setColorFilter(colorFilter: ColorFilter?) {}
    protected abstract fun onCreateAnimation(): ValueAnimator?
    override fun start() {
        if (AnimationUtils.isStarted(animator)) {
            return
        }
        animator = obtainAnimation()
        if (animator == null) {
            return
        }
        AnimationUtils.start(animator)
        invalidateSelf()
    }

    private fun obtainAnimation(): ValueAnimator? {
        if (animator == null) {
            animator = onCreateAnimation()
        }
        if (animator != null) {
            animator!!.addUpdateListener(this)
            animator!!.startDelay = animationDelay.toLong()
        }
        return animator
    }

    override fun stop() {
        if (AnimationUtils.isStarted(animator)) {
            animator!!.removeAllUpdateListeners()
            animator!!.end()
            reset()
        }
    }

    protected abstract fun drawSelf(canvas: Canvas?)
    private fun reset() {
        scale = 1f
        rotateX = 0
        rotateY = 0
        translateX = 0
        translateY = 0
        rotate = 0
        translateXPercentage = 0f
        translateYPercentage = 0f
    }

    override fun isRunning(): Boolean {
        return AnimationUtils.isRunning(animator)
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        setDrawBounds(bounds)
    }

    private fun setDrawBounds(drawBounds: Rect) {
        setDrawBounds(drawBounds.left, drawBounds.top, drawBounds.right, drawBounds.bottom)
    }

    fun setDrawBounds(left: Int, top: Int, right: Int, bottom: Int) {
        drawBounds = Rect(left, top, right, bottom)
        pivotX = getDrawBounds().centerX().toFloat()
        pivotY = getDrawBounds().centerY().toFloat()
    }

    override fun invalidateDrawable(who: Drawable) {
        invalidateSelf()
    }

    override fun scheduleDrawable(
        who: Drawable,
        what: Runnable,
        `when`: Long
    ) {
    }

    override fun unscheduleDrawable(who: Drawable, what: Runnable) {}
    override fun onAnimationUpdate(animation: ValueAnimator) {
        val callback = callback
        callback?.invalidateDrawable(this)
    }

    fun getDrawBounds(): Rect {
        return drawBounds
    }

    override fun draw(canvas: Canvas) {
        var tx = translateX
        tx = if (tx == 0) (bounds.width() * translateXPercentage).toInt() else tx
        var ty = translateY
        ty = if (ty == 0) (bounds.height() * translateYPercentage).toInt() else ty
        canvas.translate(tx.toFloat(), ty.toFloat())
        canvas.scale(scaleX, scaleY, pivotX, pivotY)
        canvas.rotate(rotate.toFloat(), pivotX, pivotY)
        if (rotateX != 0 || rotateY != 0) {
            mCamera.save()
            mCamera.rotateX(rotateX.toFloat())
            mCamera.rotateY(rotateY.toFloat())
            mCamera.getMatrix(mMatrix)
            mMatrix.preTranslate(-pivotX, -pivotY)
            mMatrix.postTranslate(pivotX, pivotY)
            mCamera.restore()
            canvas.concat(mMatrix)
        }
        drawSelf(canvas)
    }

    fun clipSquare(rect: Rect): Rect {
        val w = rect.width()
        val h = rect.height()
        val min = Math.min(w, h)
        val cx = rect.centerX()
        val cy = rect.centerY()
        val r = min / 2
        return Rect(
            cx - r,
            cy - r,
            cx + r,
            cy + r
        )
    }

    companion object {
        private val ZERO_BOUNDS_RECT = Rect()

        val ROTATE_X: Property<Sprite, Int> =
            object : IntProperty<Sprite>("rotateX") {
                override fun setValue(`object`: Sprite, value: Int) {
                    `object`.rotateX = value
                }

                override operator fun get(`object`: Sprite?): Int? {
                    return `object`?.rotateX
                }
            }
        val ROTATE: IntProperty<Sprite?> = object : IntProperty<Sprite?>("rotate") {
            override fun setValue(`object`: Sprite?, value: Int) {
                `object`?.rotate = value
            }

            override fun get(`object`: Sprite?): Int? {
                return `object`?.rotate
            }
        }
        val ROTATE_Y: IntProperty<Sprite?> =
            object : IntProperty<Sprite?>("rotateY") {
                override fun setValue(`object`: Sprite?, value: Int) {
                    `object`?.rotateY = value
                }

                override operator fun get(`object`: Sprite?): Int? {
                    return `object`?.rotateY
                }
            }
        val TRANSLATE_X_PERCENTAGE: FloatProperty<Sprite?> =
            object : FloatProperty<Sprite?>("translateXPercentage") {
                override fun setValue(`object`: Sprite?, value: Float) {
                    `object`?.translateXPercentage = value
                }

                override operator fun get(`object`: Sprite?): Float? {
                    return `object`?.translateXPercentage
                }
            }
        val TRANSLATE_Y_PERCENTAGE: FloatProperty<Sprite?> =
            object : FloatProperty<Sprite?>("translateYPercentage") {
                override fun setValue(`object`: Sprite?, value: Float) {
                    `object`?.translateYPercentage = value
                }

                override operator fun get(`object`: Sprite?): Float? {
                    return `object`?.translateYPercentage
                }
            }
        val SCALE_Y =
            object : FloatProperty<Sprite?>("scaleY") {
                override fun setValue(`object`: Sprite?, value: Float) {
                    `object`?.scaleY = value
                }

                override operator fun get(`object`: Sprite?): Float? {
                    return `object`?.scaleY
                }
            }
        val SCALE: FloatProperty<Sprite?> =
            object : FloatProperty<Sprite?>("scale") {
                override fun setValue(`object`: Sprite?, value: Float) {
                    `object`?.setScale(value)
                }

                override operator fun get(`object`: Sprite?): Float? {
                    return `object`?.getScale()
                }
            }
        val ALPHA = object : IntProperty<Sprite?>("alpha") {
            override fun setValue(`object`: Sprite?, value: Int) {
                `object`?.setAlpha(value)
            }

            override operator fun get(`object`: Sprite?): Int? {
                return `object`?.getAlpha()
            }
        }
    }

}