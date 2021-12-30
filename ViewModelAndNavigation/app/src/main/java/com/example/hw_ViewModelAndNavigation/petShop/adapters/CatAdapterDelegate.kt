package com.example.hw_ViewModelAndNavigation.petShop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hw_ViewModelAndNavigation.R
import com.example.hw_ViewModelAndNavigation.databinding.ItemAnimalBinding
import com.example.hw_ViewModelAndNavigation.petShop.Animal
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class CatAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit,
    private val onItemLongClick: (position: Int) -> Unit
) : AbsListItemAdapterDelegate<Animal.Cat, Animal, CatAdapterDelegate.CatHolder>() {

    override fun isForViewType(item: Animal, items: MutableList<Animal>, position: Int): Boolean {
        return item is Animal.Cat
    }

    override fun onCreateViewHolder(parent: ViewGroup): CatHolder {
        return CatHolder(
            ItemAnimalBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick, onItemLongClick
        )
    }

    override fun onBindViewHolder(item: Animal.Cat, holder: CatHolder, payloads: MutableList<Any>) {
        holder.bindCat(item)
    }

    class CatHolder(
        binding: ItemAnimalBinding,
        onItemClick: (position: Int) -> Unit,
        onItemLongClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val nameTextView = binding.nameTextView
        private val breedTextView = binding.breedTextView
        private val avatarImageView = binding.avatarImageView

        //инициализируем нажатия на каждый элемент списка!!!
        init {
            binding.root.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
            binding.root.setOnLongClickListener {
                onItemLongClick(bindingAdapterPosition)
                true
            }
        }

        fun bindCat(animalCat: Animal.Cat) {
            "Name: ${animalCat.name}".also { nameTextView.text = it }
            "Breed: ${animalCat.breed}".also { breedTextView.text = it }
            avatarImageView.load(animalCat.avatarLink) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.ic_animal)
                transformations(CircleCropTransformation())
            }
        }
    }
}