package ai.alpha.code.recycler.progress.animation

import android.util.Property

abstract class FloatProperty<T> protected constructor(name: String?) :
    Property<T, Float>(Float::class.java, name) {
    protected abstract fun setValue(`object`: T, value: Float)
    override fun set(`object`: T, value: Float) {
        setValue(`object`, value)
    }
}