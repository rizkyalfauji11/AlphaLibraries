package ai.alpha.code.recycler.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AlphaRecyclerViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(data: T, listener: (T) -> Unit) {
        initialComponent(data)
        onClickListener(data, listener)
    }

    abstract fun initialComponent(data: T)

    open fun onClickListener(data: T, listener: (T) -> Unit){
        itemView.setOnClickListener {
            listener(data)
        }
    }
}