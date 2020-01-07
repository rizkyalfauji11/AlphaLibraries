package ai.alpha.code.alphalibraries

import ai.alpha.code.recycler.adapter.AlphaRecyclerViewAdapter
import ai.alpha.code.recycler.adapter.AlphaRecyclerViewHolder
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_sample.view.*

class SampleAdapter(
    dataList: List<SampleModel>,
    layout: Int,
    listener: (SampleModel) -> Unit
) :
    AlphaRecyclerViewAdapter<SampleModel>(dataList, layout, listener) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlphaRecyclerViewHolder<SampleModel> = ViewHolder(setUpItemLayout(parent))


    inner class ViewHolder(view: View) : AlphaRecyclerViewHolder<SampleModel>(view) {
        private val textSample = view.textSample

        override fun initialComponent(data: SampleModel) {
            textSample.text = data.name
        }
    }
}