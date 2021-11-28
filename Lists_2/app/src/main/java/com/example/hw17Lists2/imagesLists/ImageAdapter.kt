package com.example.hw17Lists2.imagesLists

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw17Lists2.R
import com.example.hw17Lists2.databinding.ItemImageBinding

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    private var images: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int = images.size

    @SuppressLint("NotifyDataSetChanged")
    fun setImages(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imageView = binding.imageView
        fun bind(imageUrl: String) {
            imageView.load(imageUrl) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.placeholder_transparent)
            }
        }

    }
}