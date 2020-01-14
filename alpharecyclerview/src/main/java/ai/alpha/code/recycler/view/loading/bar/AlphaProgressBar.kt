package ai.alpha.code.recycler.view.loading.bar

import ai.alpha.code.recycler.R
import ai.alpha.code.recycler.view.loading.Sprite
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class AlphaProgressBar : ProgressBar {

    private var mStyle: Style? = null
    private var mColor: Int = 0
    private var mSprite: Sprite? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?) : this(context, null)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.attr.alphaSpinnerStyle
    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr, R.style.AlphaProgress_Style
    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        val a = context?.obtainStyledAttributes(
            attrs, R.styleable.AlphaProgressBar, defStyleAttr,
            defStyleRes
        )
        mStyle = Style.values()[(a as TypedArray).getInt(
            R.styleable.AlphaProgressBar_AlphaProgress_Style,
            0
        )]
        mColor = a.getColor(
            R.styleable.AlphaProgressBar_AlphaProgress_Color,
            ContextCompat.getColor(context, android.R.color.holo_blue_bright)

        )
        a.recycle()
        init()
        isIndeterminate = true
    }

    private fun init() {
        val sprite = SpriteFactory.create()
        sprite.color = mColor
        setIndeterminateDrawable(sprite)
    }

    override fun setIndeterminateDrawable(d: Drawable) {
        require(d is Sprite) { "this d must be instance of Sprite" }
        setIndeterminateDrawable(d)
    }

    private fun setIndeterminateDrawable(d: Sprite) {
        super.setIndeterminateDrawable(d)
        mSprite = d
        if (mSprite?.color == 0) {

            mSprite?.color = mColor
        }
        onSizeChanged(width, height, width, height)
        if (visibility == View.VISIBLE) {

            mSprite?.start()
        }
    }

    override fun getIndeterminateDrawable(): Sprite {
        return mSprite as Sprite
    }

    override fun unscheduleDrawable(who: Drawable) {
        super.unscheduleDrawable(who)
        if (who is Sprite) {

            who.stop()
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            if (mSprite != null && visibility == View.VISIBLE) {

                mSprite?.start()
            }
        }
    }

    override fun onScreenStateChanged(screenState: Int) {
        super.onScreenStateChanged(screenState)
        if (screenState == View.SCREEN_STATE_OFF) {

            mSprite?.stop()
        }
    }
}