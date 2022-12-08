package ru.zhdanon.skillcinema.ui.filmdetail.galleryadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.zhdanon.skillcinema.data.filmgallery.ItemImageGallery

class DiffGallery : DiffUtil.ItemCallback<ItemImageGallery>() {
    override fun areItemsTheSame(oldItem: ItemImageGallery, newItem: ItemImageGallery) =
        oldItem.previewUrl == newItem.previewUrl

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: ItemImageGallery,
        newItem: ItemImageGallery
    ) = oldItem == newItem
}