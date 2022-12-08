package ru.zhdanon.skillcinema.ui.filmdetail.galleryadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.zhdanon.skillcinema.data.filmgallery.ItemImageGallery
import ru.zhdanon.skillcinema.databinding.ItemGalleryFilmDetailBinding

class GalleryAdapter : ListAdapter<ItemImageGallery, GalleryViewHolder>(DiffGallery()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GalleryViewHolder(
        ItemGalleryFilmDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}