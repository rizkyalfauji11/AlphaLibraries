package ai.alpha.code.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AlphaRecyclerViewAdapter<T>(
    private val dataList: List<T>,
    private val layout: Int,
    private val listener: (T) -> Unit
) : RecyclerView.Adapter<AlphaRecyclerViewHolder<T>>() {

    protected fun setUpItemLayout(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: AlphaRecyclerViewHolder<T>, position: Int) {
        holder.bind(dataList[position], listener)
    }
}