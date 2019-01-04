package io.jackson.layoutplayground

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.search.*
import java.util.*

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

        storeAdapter.setListData(mutableListOf(testViewModel.storeHeaderViewModel,
                testInfoCardViewModel,
                testItemCarouselViewModel,
                testFreeDeliveryViewModel,
                testItemSproutsBrandViewModel))

        rootRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                layout_search.getLocationOnScreen(loc)
                if (loc[1] < startFadeY()) {
                    Log.d("locationOnScreen", "locactionOnScreen.y = ${loc[1]}")
                    activity?.searchBar?.visibility = View.VISIBLE
                    val fadePercentage = fadePercentage(loc[1])
                    Log.d("fadePercentage", "fadePercentage = ${fadePercentage}")
                    val alpha = percentageToAlpha(fadePercentage)
                    Log.d("alpha", "alpha = ${alpha}")
                    activity?.searchBar?.alpha = fadePercentage
                    layout_search?.visibility = View.VISIBLE
                    if (loc[1] < endFadeY()) {
                        layout_search?.visibility = View.GONE
                    } else {
                        layout_search?.visibility = View.VISIBLE
                    }
                } else {
                    activity?.searchBar?.visibility = View.GONE
                }
            }
        })

    }


    val loc = IntArray(2)
    fun startFadeY(): Int {
        val loc = IntArray(2)
        activity?.toolbar?.getLocationOnScreen(loc)
        return loc[1] + (activity?.toolbar?.height ?: 0)
    }

    fun endFadeY(): Int {
        return 32.px
    }

    fun fadePercentage(currentY: Int): Float {
        return if (startFadeY() - currentY > 0) {
            (startFadeY() - currentY) / (endFadeY()).toFloat()
        } else {
            0F
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

data class InfoCardViewModel(val bckgrndImageUrl: String,
                             val infoIconImageUrl: String,
                             val title: String,
                             @ColorRes val tintColor: Int,
                             val subTitle: String)

data class FreeDeliveryCardViewModel(val bckgrndImageUrl: String,
                                     val title: String,
                                     val subTitle: String,
                                     val storeIcons: List<StoreIcon>)

data class StoreIcon(val iconUrl: String,
                     val name: String)

data class ItemCarouselViewModel(
        val title: String,
        val items: List<Item>)

data class Item(val imageUrl: String,
                val discountPrice: String? = null,
                val priceOrg: String,
                val discount: String? = null,
                val name: String,
                val quantity: String,
                val id: String = UUID.randomUUID().toString())

val testViewModel = InstacartViewModel(storeHeaderViewModel = StoreHeaderViewModel(
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
        tintColor = R.color.infoCardYellow,
        bckgrndImageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/card-background@2x.png?alt=media",
        infoIconImageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/coupons-icon@2x.png?alt=media"
)

val testFreeDeliveryViewModel = FreeDeliveryCardViewModel(
        title = "Free Delivery",
        subTitle = "with select items",
        bckgrndImageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/card-background@2x.png?alt=media",
        storeIcons = listOf(
                StoreIcon(
                        iconUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Flogo_sprout.png?alt=media",
                        name = "Sprouts"),
                StoreIcon(
                        iconUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fbevmo.png?alt=media",
                        name = "Bevmo"),
                StoreIcon(
                        iconUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fbi_rite.png?alt=media",
                        name = "Bi-rite")
        )
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
val testItemSproutsBrandViewModel = ItemCarouselViewModel(
        title = "Sprouts Brand",
        items = listOf(
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/sprout%2Flarge_29b20190-7b24-4b7c-b5e8-2b556d51552b.png?alt=media",
                        priceOrg = "$2.99",
                        name = "Sprouts Large Brown Cage Free Grade A Eggs",
                        quantity = "12 ct"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/sprout%2Flarge_29b20190-7b24-4b7c-b5e8-2b556d51552b.png?alt=media",
                        priceOrg = "$4.19",
                        name = "Sprouts Large Cage Free Grade A Brown Eggs",
                        quantity = "12 ct"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/sprout%2Flarge_a362b7eb-16f5-4a33-8bdc-d14da1a8e1d1.png?alt=media",
                        priceOrg = "$1.99 each",
                        name = "Sprouts Spinach",
                        quantity = "9 oz"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fsprout%2Fsuggestions%2Ffield_roast2.png?alt=media",
                        discountPrice = "$3.50",
                        discount = "$0.49",
                        priceOrg = "$3.99",
                        name = "Sprouts Drinking Water",
                        quantity = "24 ct"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/sprout%2Flarge_9877cba0-4f49-4d01-869b-166361ec517f.png?alt=media",
                        priceOrg = "$3.99",
                        name = "Sprouts Organic Large Grade A Brown Eggs",
                        quantity = "12 oz"
                ),
                Item(
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/jackson-ui-demos.appspot.com/o/stores%2Fsprout%2Fsuggestions%2Fbeyond_meat.jpg?alt=media",
                        priceOrg = "$3.49",
                        name = "Sprouts Whole Milk",
                        quantity = "128 fl oz"
                )
        ))