package com.skillbox.hw_scopedstorage.presentation.videoList.videoListAdapter

import android.view.ViewGroup
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.data.Video
import com.skillbox.hw_scopedstorage.databinding.ItemVideoBinding
import com.skillbox.hw_scopedstorage.utils.inflate

class VideoAdapterDelegate(
    private val onDeleteVideo: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Video, Video, VideoAdapterDelegate.Holder>() {

    override fun isForViewType(item: Video, items: MutableList<Video>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(ItemVideoBinding::inflate), onDeleteVideo)
    }

    override fun onBindViewHolder(item: Video, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemVideoBinding,
        onDeleteVideo: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentVideoId: Long? = null

        init {
            binding.deleteButton.setOnClickListener {
                currentVideoId?.let(onDeleteVideo)
            }
        }

        fun bind(item: Video) {
            currentVideoId = item.id
            with(binding) {
                videoNameTextView.text = item.name
                sizeTextView.text = "${item.size} bytes"
                durationTextView.text = "${item.duration} "

//                videoImageView.load(item.uri){
//                    placeholder(R.drawable.placeholder_video)
//                }

                Glide.with(videoImageView)
                    .load(item.uri)
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.placeholder_video)
                    .transform(CircleCrop())
                    .into(videoImageView)

            }
        }
    }

}