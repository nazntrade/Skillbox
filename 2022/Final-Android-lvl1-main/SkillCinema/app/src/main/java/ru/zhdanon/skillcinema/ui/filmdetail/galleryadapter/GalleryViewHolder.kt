package ru.zhdanon.skillcinema.ui.filmdetail.galleryadapter

import androidx.recyclerview.widget.RecyclerView
import ru.zhdanon.skillcinema.app.loadImage
import ru.zhdanon.skillcinema.data.filmgallery.ItemImageGallery
import ru.zhdanon.skillcinema.databinding.ItemGalleryFilmDetailBinding

class GalleryViewHolder(
    private val binding: ItemGalleryFilmDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: ItemImageGallery) {
        binding.galleryImageFilmDetail.loadImage(item.previewUrl)
    }
}