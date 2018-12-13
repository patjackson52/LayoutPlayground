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

        storeAdapter.setListData(mutableListOf(textViewModel.storeHeaderViewModel, testInfoCardViewModel, testItemCarouselViewModel))
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

data class InfoCardViewModel(val bckgrndImageUrl: String,
                             val infoIconImageUrl: String,
                             val title: String,
                             val subTitle: String)

data class ItemCarouselViewModel(
        val title: String,
        val items: List<Item>)

data class Item(val imageUrl: String,
                val discountPrice: String? = null,
                val priceOrg: String,
                val discount: String? = null,
                val name: String,
                val quantity: String)

val textViewModel = InstacartViewModel(storeHeaderViewModel = StoreHeaderViewModel(
        title = "Sprouts Farmers Market",
        subTitle = "Stores in 94016",
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Flogo_sprout.png?alt=media",
        bckgrndImageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/grocery_bckgnd.jpg?alt=media",
        withInTime = "Within 2 hours",
        moreInfoString = "Everyday store prices • More info",
        searchText = "Search Sprouts Farmers Market"))

val testInfoCardViewModel = InfoCardViewModel(
        title = "Coupon savings",
        subTitle = "Up to 40% off everyday essentials",
        bckgrndImageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/card-background@2x.png?alt=media",
        infoIconImageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/coupons-icon@2x.png?alt=media"
)

val testItemCarouselViewModel = ItemCarouselViewModel(
        title = "Based on your cart",
        items = listOf(
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fsprout%2Fsuggestions%2Fbeefless_tips.png?alt=media",
                        discountPrice = "$3.56",
                        priceOrg = "$5.49",
                        discount = "1.93 off",
                        name = "Garden Home Style Beefless Tips",
                        quantity = "255 g"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fsprout%2Fsuggestions%2Ffield_roast.png?alt=media",
                        priceOrg = "$5.29",
                        name = "Field Roast Vegan Apple Maple Breakfast Sausage",
                        quantity = "9.31 oz"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fsprout%2Fsuggestions%2Fchx_tenders.JPG?alt=media",
                        discountPrice = "$3.56",
                        priceOrg = "$5.49",
                        discount = "$1.93",
                        name = "Garden Seven Grain Crispy Tenders",
                        quantity = "9 oz"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fsprout%2Fsuggestions%2Ffield_roast2.png?alt=media",
                        priceOrg = "$5.79",
                        name = "Field Roast Smoked Apple Sage Sausages",
                        quantity = "12.95 oz"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fsprout%2Fsuggestions%2Fbeyond_meat.jpg?alt=media",
                        discountPrice = "$3.79",
                        priceOrg = "$4.49",
                        name = "Lightlife Veggie Smart Dogs Protein Links",
                        quantity = "12 oz"
                )
        ))