package ai.alpha.code.alphalibraries

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dataList = mutableListOf<SampleModel>()
        val adapter = SampleAdapter(dataList, R.layout.item_sample) {
            Log.e("Data", it?.name)
        }

        rvSample.adapter = adapter
        rvSample.setSingleGrid(LinearLayoutManager.VERTICAL)

        dataList.add(SampleModel("Rizky"))
        dataList.add(SampleModel("Alfa"))
        dataList.add(SampleModel("Uji"))

        adapter.notifyDataSetChanged()
    }
}
