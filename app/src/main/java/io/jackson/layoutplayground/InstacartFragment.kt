package io.jackson.layoutplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*

class InstacartFragment : Fragment() {

    private lateinit var storeAdapter: StoreRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        storeAdapter = StoreRecyclerViewAdapter()
        rootRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
            adapter = storeAdapter
        }
    }
}

data class InstacartViewModel(val storeHeaderViewModel: StoreHeaderViewModel)
data class StoreHeaderViewModel(val title: String,
                                val subTitle: String,
                                val imageUrl: String,
                                val bckgrndImageUrl: String,
                                val withInTime: String,
                                val moreInfoString: String,
                                val searchText: String)

val textViewModel = InstacartViewModel(StoreHeaderViewModel(
        title = "Sprouts Farmers Market",
        subTitle = "Stores in 94016",
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Flogo_sprout.png?alt=media",
        bckgrndImageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/grocery_bckgnd.jpg?alt=media",
        withInTime = "Within 2 hours",
        moreInfoString = "Everyday store prices • More info",
        searchText = "Search Sprouts Farmers Market"))