package io.jackson.layoutplayground

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.item_store_header.view.*

class StoreRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var headerData = textViewModel.storeHeaderViewModel
    var data = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_store_header -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_store_header, parent, false))
            else -> throw AssertionError("onCreateViewHolder not implemented for viewType: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bindViews(headerData)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) R.layout.item_store_header else throw AssertionError("type not specified")
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