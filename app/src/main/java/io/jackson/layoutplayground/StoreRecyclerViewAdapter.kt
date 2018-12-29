package io.jackson.layoutplayground

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.item_info_card.view.*
import kotlinx.android.synthetic.main.item_store_header.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.jackson.layoutplayground.carousel.CarouselItemAdapter
import io.jackson.layoutplayground.carousel.FreeDeliveryItemAdapter
import kotlinx.android.synthetic.main.item_carousel.view.*
import kotlinx.android.synthetic.main.item_free_delivery_card.view.*
import kotlinx.android.synthetic.main.item_free_delivery_store.view.*
import kotlinx.android.synthetic.main.item_free_delivery_title.view.*
import kotlinx.android.synthetic.main.search.view.*


class StoreRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_store_header -> HeaderViewHolder(view)
            R.layout.item_info_card -> InfoCardViewHolder(view)
            R.layout.item_carousel -> CarouselItemViewHolder(view)
            R.layout.item_free_delivery_card -> FreeDeliveryCardViewHolder(view)
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
            is FreeDeliveryCardViewHolder -> holder.bindViews(data[position] as FreeDeliveryCardViewModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is StoreHeaderViewModel -> R.layout.item_store_header
            is InfoCardViewModel -> R.layout.item_info_card
            is ItemCarouselViewModel -> R.layout.item_carousel
            is FreeDeliveryCardViewModel -> R.layout.item_free_delivery_card
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

class FreeDeliveryCardViewHolder(view: View) : BindingViewHolder<FreeDeliveryCardViewModel>(view) {
    private val backgndTint by lazy { ContextCompat.getColor(itemView.context, R.color.infoCardBlue) }

    override fun bindViews(data: FreeDeliveryCardViewModel) {

        with(data) {
            with(itemView) {
                GlideApp.with(itemView.context)
                        .load(bckgrndImageUrl)
                        .into(object : SimpleTarget<Drawable>() {
                            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                itemView.post {
//                                    resource.setTint(backgndTint)
                                    layout_free_delivery_root.setBackgroundColor(backgndTint)
                                    carouselFreeDeliveryRecyclerView.background = resource
                                }
                            }
                        })

                carouselFreeDeliveryRecyclerView.apply {
                    layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = FreeDeliveryItemAdapter().apply {
                        this.data = data
                    }
                }
            }
        }
    }
}

class FreeDeliveryStoreItemHolder(view: View) : BindingViewHolder<StoreIcon>(view) {
    override fun bindViews(data: StoreIcon) {
        GlideApp.with(itemView.context)
                .load(data.iconUrl)
                .circleCrop()
                .into(itemView.imgStoreIcon)

    }

}


class FreeDeliveryTitleHolder(view: View) : BindingViewHolder<FreeDeliveryCardViewModel>(view) {
    override fun bindViews(data: FreeDeliveryCardViewModel) {
        with(itemView) {
            txt_free_delivery_title.text = data.title
            txt_free_delivery_subtitle.text = data.subTitle
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