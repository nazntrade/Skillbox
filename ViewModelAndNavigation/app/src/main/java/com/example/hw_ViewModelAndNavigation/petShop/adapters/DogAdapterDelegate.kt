package com.example.hw_ViewModelAndNavigation.petShop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hw_ViewModelAndNavigation.databinding.ItemDogBinding
import com.example.hw_ViewModelAndNavigation.R
import com.example.hw_ViewModelAndNavigation.petShop.Animal
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class DogAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit,
    private val onItemLongClick: (id: Long) -> Unit
) : AbsListItemAdapterDelegate<Animal.Dog, Animal, DogAdapterDelegate.DogHolder>() {

    override fun isForViewType(item: Animal, items: MutableList<Animal>, position: Int): Boolean {
        return item is Animal.Dog
    }

    override fun onCreateViewHolder(parent: ViewGroup): DogHolder {
        return DogHolder(
            ItemDogBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick, onItemLongClick
        )
    }

    override fun onBindViewHolder(item: Animal.Dog, holder: DogHolder, payloads: MutableList<Any>) {
        holder.bindDog(item)
    }

    class DogHolder(
        binding: ItemDogBinding,
        onItemClick: (position: Int) -> Unit,
        onItemLongClick: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val nameTextView = binding.nameTextView
        private val breedTextView = binding.breedTextView
        private val avatarImageView = binding.avatarImageView
        private val dogSkillTextView = binding.dogSkillTextView

        //инициализируем нажатия на каждый элемент списка!!!
        private var currentId: Long? = null
        init {
            binding.root.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
            binding.root.setOnLongClickListener {
                currentId?.let {
                    onItemLongClick(it)
                }
                true
            }
        }

        fun bindDog(animalDog: Animal.Dog) {
            "Name: ${animalDog.name}".also { nameTextView.text = it }
            "Breed: ${animalDog.breed}".also { breedTextView.text = it }
            avatarImageView.load(animalDog.avatarLink) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.ic_animal)
                transformations(CircleCropTransformation())
            }
            "Skill: ${animalDog.skill}".also { dogSkillTextView.text = it }
        }
    }
}