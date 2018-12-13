package io.jackson.layoutplayground.carousel

import android.content.Context
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


/**
 * Viewholder for individual item in a horizontal carousel of items
 */
class ItemViewHolder(view: View): BindingViewHolder<Item>(view) {

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