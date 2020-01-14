package ai.alpha.code.recycler.view.loading

import ai.alpha.code.recycler.progress.animation.AnimationUtils
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Rect

abstract class SpriteContainer internal constructor() : Sprite() {
    private val sprites: Array<Sprite>?
    override var color = 0
        set(color) {
            field = color
            for (i in 0 until childCount) {
                getChildAt(i)?.color = color
            }
        }

    private fun initCallBack() {
        if (sprites != null) {
            for (sprite in sprites) {
                sprite.callback = this
            }
        }
    }

    open fun onChildCreated(sprites: Array<Sprite>?) {}
    val childCount: Int
        get() = sprites?.size ?: 0

    fun getChildAt(index: Int): Sprite? {
        return sprites?.get(index)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawChild(canvas)
    }

    private fun drawChild(canvas: Canvas) {
        if (sprites != null) {
            for (sprite in sprites) {
                val count = canvas.save()
                sprite.draw(canvas)
                canvas.restoreToCount(count)
            }
        }
    }

    override fun drawSelf(canvas: Canvas?) {}

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        if (sprites != null) {
            for (sprite in sprites) {
                sprite.bounds = bounds
            }
        }
    }

    override fun start() {
        super.start()
        AnimationUtils.start(sprites)
    }

    override fun stop() {
        super.stop()
        AnimationUtils.stop(sprites)
    }

    override fun isRunning(): Boolean {
        return AnimationUtils.isRunning(sprites) || super.isRunning()
    }

    protected abstract fun onCreateChild(): Array<Sprite>?
    override fun onCreateAnimation(): ValueAnimator? {
        return null
    }

    init {
        sprites = onCreateChild()
        initCallBack()
        onChildCreated(sprites)
    }
}