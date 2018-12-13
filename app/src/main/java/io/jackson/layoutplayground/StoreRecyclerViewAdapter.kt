package io.jackson.layoutplayground

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.item_info_card.view.*
import kotlinx.android.synthetic.main.item_store_header.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.jackson.layoutplayground.carousel.CarouselItemAdapter
import kotlinx.android.synthetic.main.item_carousel.view.*
import kotlinx.android.synthetic.main.search.view.*


class StoreRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_store_header -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_store_header, parent, false))
            R.layout.item_info_card -> InfoCardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_info_card, parent, false))
            R.layout.item_carousel -> CarouselItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false))
            else -> throw AssertionError("onCreateViewHolder not implemented for viewType: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bindViews(data[position] as StoreHeaderViewModel)
            is InfoCardViewHolder -> holder.bindViews(data[position] as InfoCardViewModel)
            is CarouselItemViewHolder -> holder.bindViews(data[position] as ItemCarouselViewModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is StoreHeaderViewModel -> R.layout.item_store_header
            is InfoCardViewModel -> R.layout.item_info_card
            is ItemCarouselViewModel -> R.layout.item_carousel
            else -> throw AssertionError("type not specified")
        }
    }


    fun setListData(data: MutableList<Any>) {
        this.data = data
    }
}

abstract class BindingViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindViews(data: T)

}

class HeaderViewHolder(view: View) : BindingViewHolder<StoreHeaderViewModel>(view) {

    override fun bindViews(data: StoreHeaderViewModel) {
        with(data) {
            with(itemView) {
                txtStoreTitle.text = title
                txtSubTitle.text = subTitle
                txtWithinTime.text = withInTime
                txtMoreInfo.text = moreInfoString
                txtSearch.text = searchText
                GlideApp.with(itemView)
                        .load(imageUrl)
                        .transform(CircleCrop())
                        .into(imgStore)
                GlideApp.with(itemView)
                        .load(bckgrndImageUrl)
                        .into(imgBckgrnd)
//                ImageViewCompat.setImageTintList(imgBckgrnd, ColorStateList.valueOf(ContextCompat.getColor(itemView.context, android.R.color.darker_gray)))
            }
        }

    }

}

class InfoCardViewHolder(view: View) : BindingViewHolder<InfoCardViewModel>(view) {

    override fun bindViews(data: InfoCardViewModel) {
        val roundedCornersDrawable = ContextCompat.getDrawable(itemView.context, R.drawable.rounded_outline_8dp)
        roundedCornersDrawable!!.setTint(ContextCompat.getColor(itemView.context, R.color.infoCardYellow))

        with(data) {
            with(itemView) {
                txtTitle.text = title
                txtSubtitle.text = subTitle
                GlideApp.with(imgCardBckgrnd)
                        .load(bckgrndImageUrl)
                        .transform(RoundedCornersTransformation(itemView.context, 8, 8))
                        .into(imgCardBckgrnd)
                imgCardBckgrnd.background = roundedCornersDrawable
                imgCardBckgrnd.clipToOutline = true
                GlideApp.with(itemView)
                        .load(infoIconImageUrl)
                        .transform(CircleCrop())
                        .into(imgInfoIcon)
            }
        }

    }

}

class CarouselItemViewHolder(view: View) : BindingViewHolder<ItemCarouselViewModel>(view) {

    override fun bindViews(data: ItemCarouselViewModel) {
        itemView.txtCarouselTitle.text = data.title
        itemView.carouselRecyclerView.apply {
            layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = CarouselItemAdapter().apply {
                this.data = data.items.toMutableList()
            }
        }
    }

}