package ai.alpha.code.recycler.view.loading

import ai.alpha.code.recycler.progress.animation.SpriteAnimatorBuilder
import android.animation.ValueAnimator
import android.graphics.Rect

class ThreeBounce : SpriteContainer() {
    override fun onCreateChild(): Array<Sprite> {
        return arrayOf(
            Bounce(),
            Bounce(),
            Bounce()
        )
    }

    override fun onChildCreated(sprites: Array<Sprite>?) {
        super.onChildCreated(sprites)
        sprites?.get(1)?.setAnimationDelay(160)
        sprites?.get(2)?.setAnimationDelay(320)
    }

    override fun onBoundsChange(bounds: Rect) {
        var newBounds = bounds
        super.onBoundsChange(newBounds)
        newBounds = clipSquare(newBounds)
        val radius = newBounds.width() / 8
        val top = newBounds.centerY() - radius
        val bottom = newBounds.centerY() + radius
        for (i in 0 until childCount) {
            val left = (newBounds.width() * i / 3
                    + newBounds.left)
            getChildAt(i)!!.setDrawBounds(
                left, top, left + radius * 2, bottom
            )
        }
    }

    private inner class Bounce internal constructor() : CircleSprite() {
        override fun onCreateAnimation(): ValueAnimator? {
            val fractions = floatArrayOf(0f, 0.4f, 0.8f, 1f)
            return SpriteAnimatorBuilder(this).scale(fractions, arrayOf(0f, 1f, 0f, 0f)).duration(1400)
                .easeInOut(*fractions)
                .build()
        }

        init {
            setScale(0f)
        }
    }
}