package io.jackson.layoutplayground

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.item_info_card.view.*
import kotlinx.android.synthetic.main.item_store_header.view.*
import android.graphics.Shader
import android.R.attr.resource
import android.content.res.ColorStateList
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.search.view.*


class StoreRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_store_header -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_store_header, parent, false))
            R.layout.item_info_card -> InfoCardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_info_card, parent, false))
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
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is StoreHeaderViewModel -> R.layout.item_store_header
            is InfoCardViewModel -> R.layout.item_info_card
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
        val roundedCornersDrawable = itemView.resources.getDrawable(R.drawable.rounded_outline_8dp)
        roundedCornersDrawable.setTint(ContextCompat.getColor(itemView.context, R.color.infoCardYellow))

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