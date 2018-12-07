package io.jackson.layoutplayground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.ic_item.*

class AlbumListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_album_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setItemViewModel(demoItemViewModel)
    }

    fun setItemViewModel(viewModel: ItemViewModel) {
        txt_title.text = viewModel.title
        txt_subtitle.text = viewModel.subTitle
        GlideApp.with(this)
                .load(viewModel.imageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(this.activity, 15, 2, "#7D9067", 10)))
                .into(img_item)
    }
}