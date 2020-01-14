package ai.alpha.code.recycler.progress.animation

import ai.alpha.code.recycler.view.loading.Sprite
import android.animation.Animator
import android.animation.ValueAnimator

class AnimationUtils {

    companion object {
        fun start(animator: Animator?) {
            if (animator != null && !animator.isStarted) {
                animator.start()
            }
        }
        @Suppress("unused")
        fun stop(animator: Animator?) {
            if (animator != null && !animator.isRunning) {
                animator.end()
            }
        }

        fun start(sprites: Array<Sprite>?) {
            if (sprites != null) {
                for (sprite in sprites) {
                    sprite.start()
                }
            }
        }

        fun stop(sprites: Array<Sprite>?) {
            if (sprites != null) {
                for (sprite in sprites) {
                    sprite.stop()
                }
            }
        }

        fun isRunning(sprites: Array<Sprite>?): Boolean {
            if (sprites != null) {
                for (sprite in sprites) {
                    if (sprite.isRunning) {
                        return true
                    }
                }
            }
            return false
        }

        fun isRunning(animator: ValueAnimator?): Boolean {
            return animator != null && animator.isRunning
        }

        fun isStarted(animator: ValueAnimator?): Boolean {
            return animator != null && animator.isStarted
        }
    }
}