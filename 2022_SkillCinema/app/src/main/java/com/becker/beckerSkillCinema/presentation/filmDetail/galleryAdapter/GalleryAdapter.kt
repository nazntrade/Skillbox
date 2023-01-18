package com.becker.beckerSkillCinema.presentation.filmDetail.galleryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.databinding.ItemGalleryFilmDetailBinding
import com.becker.beckerSkillCinema.utils.loadImage

class GalleryAdapter(
    private val maxListSize: Int,
    private val clickNextButton: () -> Unit,
    private val clickItem: (imageUrl: String) -> Unit /////////////////////////
) : ListAdapter<ItemImageGallery, GalleryAdapter.GalleryViewHolder>(DiffGallery()) {

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
//        holder.bindItem(getItem(position))
        if (position == maxListSize - 1) {
            holder.bindNextShow { clickNextButton() }
        } else {
            holder.bindItem(getItem(position)) { item -> clickItem(item) }////////////////
        }
    }

    class GalleryViewHolder(
        private val binding: ItemGalleryFilmDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindNextShow(clickNextButton: () -> Unit) {
            binding.apply {
                showAll.isVisible = true
                itemImage.isVisible = false
            }
            binding.btnArrowShowAll.setOnClickListener { clickNextButton() }
        }

        fun bindItem(item: ItemImageGallery, clickItem: (imageUrl: String) -> Unit) {
            binding.itemImage.loadImage(item.previewUrl)
            binding.showAll.isVisible = false
            binding.itemImage.isVisible = true
            binding.itemImage.setOnClickListener { clickItem(item.imageUrl) }/////////
        }
    }
}