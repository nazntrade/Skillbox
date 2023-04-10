package com.becker.beckerSkillCinema.presentation.filmDetail.gallery.pagerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.becker.beckerSkillCinema.R
import com.becker.beckerSkillCinema.data.filmGallery.ItemImageGallery
import com.becker.beckerSkillCinema.databinding.ItemGalleryFullscreenBinding
import com.becker.beckerSkillCinema.presentation.filmDetail.gallery.recyclerAdapter.GalleryFullAdapter

class GalleryFullscreenAdapter :
    PagingDataAdapter<ItemImageGallery, GalleryFullscreenAdapter.GalleryFullscreenViewHolder>(
        GalleryFullAdapter.GalleryFullDiffUtil()
    ) {
    override fun onBindViewHolder(holder: GalleryFullscreenViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {

                // by using Coil instead of Glide
                galleryImageFullscreen.load(item.imageUrl) {
                    placeholder(R.drawable.download_place_holder)
                    error(R.drawable.no_poster)
                }
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