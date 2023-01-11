package com.becker.beckerSkillCinema.presentation.filmDetail.galleryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.databinding.ItemGalleryFilmDetailBinding
import com.becker.beckerSkillCinema.utils.loadImage

class GalleryAdapter :
    ListAdapter<ItemImageGallery, GalleryAdapter.GalleryViewHolder>(DiffGallery()) {

    class DiffGallery : DiffUtil.ItemCallback<ItemImageGallery>() {
        override fun areItemsTheSame(oldItem: ItemImageGallery, newItem: ItemImageGallery) =
            oldItem.previewUrl == newItem.previewUrl

        override fun areContentsTheSame(
            oldItem: ItemImageGallery,
            newItem: ItemImageGallery
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GalleryViewHolder(
        ItemGalleryFilmDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    class GalleryViewHolder(
        private val binding: ItemGalleryFilmDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: ItemImageGallery) {
            binding.galleryImageFilmDetail.loadImage(item.previewUrl)
        }
    }
}