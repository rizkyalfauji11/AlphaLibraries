package ai.alpha.code.recycler.progress.animation

import ai.alpha.code.recycler.progress.animation.interpolar.KeyFrameInterpolator
import ai.alpha.code.recycler.view.loading.Sprite
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.util.Log
import android.util.Property
import android.view.animation.Animation
import android.view.animation.Interpolator
import java.util.*


class SpriteAnimatorBuilder(private val sprite: Sprite) {
    private var interpolator: Interpolator? = null
    private val repeatCount = Animation.INFINITE
    private var duration: Long = 2000
    private var startFrame = 0
    private val fds: MutableMap<String, FrameData<*>> =
        HashMap()

    internal open inner class FrameData<T>(
        val fractions: FloatArray?,
        val property: Property<*, *>,
        val values: Array<T>
    )

    internal inner class IntFrameData(
        fractions: FloatArray?,
        property: Property<*, *>,
        values: Array<Int?>
    ) :
        FrameData<Int?>(fractions, property, values)

    internal inner class FloatFrameData(
        fractions: FloatArray?,
        property: Property<*, *>,
        values: Array<Float?>
    ) :
        FrameData<Float?>(fractions, property, values)

    fun scale(fractions: FloatArray?, scale: Array<Float?>): SpriteAnimatorBuilder {
        holder(fractions, Sprite.SCALE, scale)
        return this
    }

    fun alpha(fractions: FloatArray?, alpha: Array<Int?>): SpriteAnimatorBuilder {
        holder(fractions, Sprite.ALPHA, alpha)
        return this
    }

    fun scaleY(fractions: FloatArray?, scaleY: Array<Float?>): SpriteAnimatorBuilder {
        holder(fractions, Sprite.SCALE_Y, scaleY)
        return this
    }

    fun rotateX(fractions: FloatArray?, rotateX: Array<Int?>): SpriteAnimatorBuilder {
        holder(fractions, Sprite.ROTATE_X, rotateX)
        return this
    }

    fun rotateY(fractions: FloatArray?, rotateY: Array<Int?>): SpriteAnimatorBuilder {
        holder(fractions, Sprite.ROTATE_Y, rotateY)
        return this
    }

    fun rotate(fractions: FloatArray?, rotate: Array<Int?>): SpriteAnimatorBuilder {
        holder(fractions, Sprite.ROTATE, rotate)
        return this
    }

    fun translateXPercentage(
        fractions: FloatArray?,
        translateXPercentage: Array<Float?>
    ): SpriteAnimatorBuilder {
        holder(fractions, Sprite.TRANSLATE_X_PERCENTAGE, translateXPercentage)
        return this
    }

    fun translateYPercentage(
        fractions: FloatArray?,
        translateYPercentage: Array<Float?>
    ): SpriteAnimatorBuilder {
        holder(fractions, Sprite.TRANSLATE_Y_PERCENTAGE, translateYPercentage)
        return this
    }

    private fun holder(
        fractions: FloatArray?,
        property: Property<*, *>,
        values: Array<Float?>
    ) {
        ensurePair(fractions?.size, values.size)
        fds[property.name] = FloatFrameData(fractions, property, values)
    }


    private fun holder(
        fractions: FloatArray?,
        property: Property<*, *>,
        values: Array<Int?>
    ) {
        ensurePair(fractions?.size, values.size)
        fds[property.name] = IntFrameData(fractions, property, values)
    }

    private fun ensurePair(fractionsLength: Int?, valuesLength: Int) {
        check(fractionsLength == valuesLength) {
            String.format(
                Locale.getDefault(),
                "The fractions.length must equal values.length, " +
                        "fraction.length[%d], values.length[%d]",
                fractionsLength,
                valuesLength
            )
        }
    }

    private fun interpolator(interpolator: Interpolator) {
        this.interpolator = interpolator
    }

    fun easeInOut(vararg fractions: Float): SpriteAnimatorBuilder {
        interpolator(
            KeyFrameInterpolator.easeInOut(
                fractions
            )
        )
        return this
    }

    fun duration(duration: Long): SpriteAnimatorBuilder {
        this.duration = duration
        return this
    }

    fun startFrame(startFrame: Int): SpriteAnimatorBuilder {
        var newStartFrame = startFrame
        if (newStartFrame < 0) {
            Log.w(
                TAG,
                "startFrame should always be non-negative"
            )
            newStartFrame = 0
        }
        this.startFrame = newStartFrame
        return this
    }

    fun build(): ObjectAnimator? {
        val holders =
            arrayOfNulls<PropertyValuesHolder>(fds.size)
        var i = 0
        for ((_, data) in fds) {
            val keyframes = arrayOfNulls<Keyframe>(data.fractions?.size as Int)
            val fractions: FloatArray? = data.fractions
            val startF = fractions?.get(startFrame)
            for (j in startFrame until startFrame + data.values.size) {
                val key = j - startFrame
                val vk = j % data.values.size
                var fraction = fractions?.get(vk) as Float - startF as Float
                if (fraction < 0) {
                    fraction += fractions[fractions.size - 1]
                }
                if (data is IntFrameData) {
                    keyframes[key] = Keyframe.ofInt(fraction, (data.values[vk] as Int))
                } else if (data is FloatFrameData) {
                    keyframes[key] =
                        Keyframe.ofFloat(fraction, (data.values[vk] as Float))
                } else {
                    keyframes[key] = Keyframe.ofObject(fraction, data.values[vk])
                }
            }
            holders[i] = PropertyValuesHolder.ofKeyframe(data.property, *keyframes)
            i++
        }
        val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            sprite,
            *holders
        )
        animator.duration = duration
        animator.repeatCount = repeatCount
        animator.interpolator = interpolator
        return animator
    }

    companion object {
        private const val TAG = "SpriteAnimatorBuilder"
    }

}