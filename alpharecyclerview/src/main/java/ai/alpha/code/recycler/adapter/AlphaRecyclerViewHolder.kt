package ai.alpha.code.recycler.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AlphaRecyclerViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(data: T, listener: (T) -> Unit) {
        setOnItemClick(data, listener)
        initialComponent(data)
    }

    open fun initialComponent(data: T) {

    }


    protected fun setOnItemClick(data: T, listener: (T) -> Unit) {
        itemView.setOnClickListener {
            listener(data)
        }

        itemView.setOnLongClickListener {
            listener(data)
            true
        }
    }
}