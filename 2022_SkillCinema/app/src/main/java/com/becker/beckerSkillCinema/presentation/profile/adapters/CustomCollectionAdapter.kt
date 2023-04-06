package com.becker.beckerSkillCinema.presentation.profile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.becker.beckerSkillCinema.data.localData.entities.CustomCollection
import com.becker.beckerSkillCinema.databinding.CustomCollectionInProfileBinding

open class CustomCollectionAdapter(
    val onCollectionItemClick: (CustomCollection) -> Unit,
    val onDeleteCollectionClick: (String) -> Unit
) : ListAdapter<CustomCollection, CustomSelectionViewHolder>(DiffUtilCallBackCustomCollection()) {

    class DiffUtilCallBackCustomCollection : DiffUtil.ItemCallback<CustomCollection>() {
        override fun areItemsTheSame(
            oldItem: CustomCollection, newItem: CustomCollection
        ): Boolean {
            return oldItem.collectionName == newItem.collectionName
        }

        override fun areContentsTheSame(
            oldItem: CustomCollection, newItem: CustomCollection
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomSelectionViewHolder {
        return CustomSelectionViewHolder(
            binding = CustomCollectionInProfileBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onCollectionItemClick = onCollectionItemClick,
            onDeleteCollectionClick = onDeleteCollectionClick
        )
    }

    override fun onBindViewHolder(holder: CustomSelectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class CustomSelectionViewHolder(
    val binding: CustomCollectionInProfileBinding,
    val onCollectionItemClick: (CustomCollection) -> Unit,
    val onDeleteCollectionClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CustomCollection) {
        with(binding) {
            customCollectionTitle.text = item.collectionName
            //1.0 instead of movieId I store the size of the list
            //ProfileMovieViewModel.getCustomCollections
            if (item.movieId == 0) {
                // Value of 'item.movieId' is always zero - !!!!!!!  it's WRONG
                countInCustomCollection.text = item.movieId.toString()
            } else {
                //1.0 instead of id I passed here the size of the list
                countInCustomCollection.text = (item.movieId - 1).toString()
            }
            binding.root.setOnClickListener {
                onCollectionItemClick(item)
            }
            closeButton.setOnClickListener {
                onDeleteCollectionClick(item.collectionName)
            }
        }
    }
}