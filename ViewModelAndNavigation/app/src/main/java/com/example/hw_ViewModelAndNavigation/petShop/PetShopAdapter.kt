package com.example.hw_ViewModelAndNavigation.petShop

import androidx.recyclerview.widget.DiffUtil
import com.example.hw_ViewModelAndNavigation.petShop.adapters.BirdAdapterDelegate
import com.example.hw_ViewModelAndNavigation.petShop.adapters.CatAdapterDelegate
import com.example.hw_ViewModelAndNavigation.petShop.adapters.DogAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class PetShopAdapter(
    onItemClick: (id: Long) -> Unit,
    onItemLongClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Animal>(AnimalDiffUtilCallback()) {

    init {
        delegatesManager
            .addDelegate(CatAdapterDelegate(onItemClick, onItemLongClick))
            .addDelegate(DogAdapterDelegate(onItemClick, onItemLongClick))
            .addDelegate(BirdAdapterDelegate(onItemClick, onItemLongClick))
    }

    class AnimalDiffUtilCallback : DiffUtil.ItemCallback<Animal>() {
        override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return when {
                oldItem is Animal.Cat && newItem is Animal.Cat -> oldItem.id == newItem.id
                oldItem is Animal.Dog && newItem is Animal.Dog -> oldItem.id == newItem.id
                oldItem is Animal.Bird && newItem is Animal.Bird -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean {
            return oldItem == newItem
        }
    }
}