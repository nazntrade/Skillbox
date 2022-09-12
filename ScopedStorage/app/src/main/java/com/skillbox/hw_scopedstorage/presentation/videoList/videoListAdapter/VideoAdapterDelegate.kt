package com.skillbox.hw_scopedstorage.presentation.videoList.videoListAdapter

import android.net.Uri
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.data.Video
import com.skillbox.hw_scopedstorage.databinding.ItemVideoBinding
import com.skillbox.hw_scopedstorage.utils.inflate

class VideoAdapterDelegate(
    private val onVideoClick: (clickedVideo: Video) -> Unit,
    private val onDeleteVideo: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Video, Video, VideoAdapterDelegate.Holder>() {

    override fun isForViewType(item: Video, items: MutableList<Video>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(ItemVideoBinding::inflate),onVideoClick, onDeleteVideo)
    }

    override fun onBindViewHolder(item: Video, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemVideoBinding,
        onVideoClick: (clickedVideo: Video) -> Unit,
        onDeleteVideo: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentVideo: Video? = null
        private var currentVideoId: Long? = null

        init {
            binding.root.setOnClickListener {
                currentVideo?.let(onVideoClick)
            }
            binding.deleteButton.setOnClickListener {
                currentVideoId?.let(onDeleteVideo)
            }
        }

        fun bind(item: Video) {
            currentVideo = item
            currentVideoId = item.id
            with(binding) {
                videoNameTextView.text = item.name
                sizeTextView.text = "${item.size} bytes"
                durationTextView.text = "${item.duration} "

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