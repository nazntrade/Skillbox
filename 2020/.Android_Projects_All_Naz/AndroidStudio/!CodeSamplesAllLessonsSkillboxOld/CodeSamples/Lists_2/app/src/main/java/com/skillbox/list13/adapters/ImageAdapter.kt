package com.skillbox.list13.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.list13.R
import com.skillbox.list13.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image.*

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var images: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(
                R.layout.item_image
            )
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun setImages(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(imageUrl: String) {
            Glide.with(itemView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_image)
                .into(imageView)
        }
    }
}