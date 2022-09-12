package com.skillbox.hw_scopedstorage.presentation.videoList.videoListAdapter

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skillbox.hw_scopedstorage.data.Video

class VideoAdapter (
    onVideoClick: (clickedVideo: Video) -> Unit,
    onDeleteVideo: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<Video>(VideoDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(VideoAdapterDelegate(onVideoClick, onDeleteVideo))
    }

    class VideoDiffUtilCallback: DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }

}