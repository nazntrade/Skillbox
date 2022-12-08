package ru.zhdanon.skillcinema.ui.gallery.pageradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.zhdanon.skillcinema.app.loadImage
import ru.zhdanon.skillcinema.data.filmgallery.ItemImageGallery
import ru.zhdanon.skillcinema.databinding.ItemGalleryFullscreenBinding
import ru.zhdanon.skillcinema.ui.gallery.recycleradapter.GalleryFullDiffUtil

class GalleryFullscreenAdapter :
    PagingDataAdapter<ItemImageGallery, GalleryFullscreenViewHolder>(GalleryFullDiffUtil()) {
    override fun onBindViewHolder(holder: GalleryFullscreenViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                galleryImageFullscreen.loadImage(it.previewUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryFullscreenViewHolder {
        val binding =
            ItemGalleryFullscreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryFullscreenViewHolder(binding)
    }
}