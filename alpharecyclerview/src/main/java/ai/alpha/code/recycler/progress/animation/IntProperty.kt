package ai.alpha.code.recycler.progress.animation

import android.util.Property

abstract class IntProperty<T> (name: String?) :
    Property<T, Int>(Int::class.java, name) {

    protected abstract fun setValue(`object`: T, value: Int)

    override fun set(`object`: T, value: Int) {
        setValue(`object`, value)
    }
}