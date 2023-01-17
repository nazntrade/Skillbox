package com.becker.beckerSkillCinema.presentation.gallery.pagerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.databinding.ItemGalleryFullscreenBinding
import com.becker.beckerSkillCinema.presentation.gallery.recyclerAdapter.GalleryFullAdapter
import com.becker.beckerSkillCinema.utils.loadImage

class GalleryFullscreenAdapter :
    PagingDataAdapter<ItemImageGallery, GalleryFullscreenAdapter.GalleryFullscreenViewHolder>(
        GalleryFullAdapter.GalleryFullDiffUtil()
    ) {
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

    class GalleryFullscreenViewHolder(val binding: ItemGalleryFullscreenBinding) :
        RecyclerView.ViewHolder(binding.root)
}