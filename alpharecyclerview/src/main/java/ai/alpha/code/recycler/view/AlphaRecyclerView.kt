package ai.alpha.code.recycler.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlphaRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setSingleGrid(layoutManager: Int?) {
        this.layoutManager = layoutManager?.let { LinearLayoutManager(context, it, false) }
    }

    fun setGridLayout(countGrid: Int?) {
        this.layoutManager = GridLayoutManager(context, countGrid as Int)
    }
}