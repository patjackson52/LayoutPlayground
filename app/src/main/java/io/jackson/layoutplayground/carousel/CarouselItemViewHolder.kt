package io.jackson.layoutplayground.carousel

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.jackson.layoutplayground.*
import kotlinx.android.synthetic.main.item_carousel_item.view.*
import java.lang.IllegalArgumentException


/**
 * Viewholder for individual item in a horizontal carousel of items
 */
class ItemViewHolder(view: View) : BindingViewHolder<Item>(view) {

    private val orgPriceColor by lazy { ContextCompat.getColor(itemView.context, R.color.carouselItemQuantity) }
    private val discountPriceColor by lazy { ContextCompat.getColor(itemView.context, R.color.red) }

    override fun bindViews(data: Item) {
        with(itemView) {
            GlideApp.with(itemView)
                    .load(data.imageUrl)
                    .into(imgItem)
            if (data.discountPrice != null) {
                val priceSpan = SpannableString(data.discountPrice + " " + data.priceOrg)
                priceSpan.setSpan(StrikethroughSpan(), data.discountPrice.length + 1, priceSpan.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                priceSpan.setSpan(ForegroundColorSpan(orgPriceColor), data.discountPrice.length + 2, priceSpan.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                priceSpan.setSpan(ForegroundColorSpan(discountPriceColor), 0, data.discountPrice.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                txtPrice.text = priceSpan
            } else {
                txtPrice.text = data.priceOrg
            }
            if (data.discount != null) {
                txtDiscount.visibility = View.VISIBLE
                txtDiscount.text = data.discount
            } else {
                txtDiscount.visibility = View.GONE
            }
            txtName.text = data.name
            txtQuantity.text = data.quantity
        }
    }
}

class CarouselItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    var data: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_carousel_item, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindViews(data[position])
    }

}


class FreeDeliveryItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var data: FreeDeliveryCardViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_free_delivery_title -> FreeDeliveryTitleHolder(view)
            R.layout.item_free_delivery_store -> FreeDeliveryStoreItemHolder(view)
            else -> throw IllegalArgumentException("Viewtype $viewType not handled")
        }
    }

    override fun getItemCount() = data.storeIcons.size + 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_free_delivery_title
            else -> R.layout.item_free_delivery_store
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> (holder as FreeDeliveryTitleHolder).bindViews(data)
            else -> (holder as FreeDeliveryStoreItemHolder).bindViews(data.storeIcons[position - 1])
        }
    }

}
