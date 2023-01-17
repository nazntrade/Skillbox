package com.becker.beckerSkillCinema.presentation.gallery.recyclerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.databinding.ItemGalleryImageBinding
import com.becker.beckerSkillCinema.utils.loadImage

class GalleryFullAdapter(
    private val onClick: (Int) -> Unit
) : PagingDataAdapter<ItemImageGallery, GalleryFullAdapter.GalleryFullViewHolder>(
    GalleryFullDiffUtil()
) {

    class GalleryFullDiffUtil : DiffUtil.ItemCallback<ItemImageGallery>() {
        override fun areItemsTheSame(
            oldItem: ItemImageGallery,
            newItem: ItemImageGallery
        ) = oldItem.previewUrl == newItem.previewUrl

        override fun areContentsTheSame(
            oldItem: ItemImageGallery,
            newItem: ItemImageGallery
        ) = oldItem == newItem
    }

    override fun onBindViewHolder(holder: GalleryFullViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                galleryImage.loadImage(it.previewUrl)
            }
            holder.binding.galleryImage.setOnClickListener { onClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryFullViewHolder {
        val binding =
            ItemGalleryImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryFullViewHolder(binding)
    }

    class GalleryFullViewHolder(val binding: ItemGalleryImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}