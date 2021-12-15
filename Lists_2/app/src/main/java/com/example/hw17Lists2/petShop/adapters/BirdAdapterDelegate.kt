package com.example.hw17Lists2.petShop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hw17Lists2.R
import com.example.hw17Lists2.databinding.ItemBirdBinding
import com.example.hw17Lists2.petShop.Animal
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class BirdAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit
) : AbsListItemAdapterDelegate<Animal.Bird, Animal, BirdAdapterDelegate.BirdHolder>() {

    override fun isForViewType(item: Animal, items: MutableList<Animal>, position: Int): Boolean {
        return item is Animal.Bird
    }

    override fun onCreateViewHolder(parent: ViewGroup): BirdHolder {
        return BirdHolder(
            ItemBirdBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(
        item: Animal.Bird, holder: BirdHolder, payloads: MutableList<Any>
    ) {
        holder.bindBird(item)
    }

    class BirdHolder(
        binding: ItemBirdBinding,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val nameTextView = binding.nameTextView
        private val breedTextView = binding.breedTextView
        private val avatarImageView = binding.avatarImageView
        private val songTextView = binding.songTextView
        private val alarmTextView = binding.alarmTextView
        private val discountImageView3 = binding.discountImageView3

        //инициализируем нажатия на каждый элемент списка!!!
        init {
            binding.root.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        fun bindBird(animalBird: Animal.Bird) {
            "Name: ${animalBird.name}".also { nameTextView.text = it }
            "Breed: ${animalBird.breed}".also { breedTextView.text = it }
            avatarImageView.load(animalBird.avatarLink) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.ic_animal)
                transformations(CircleCropTransformation())
            }
            "Sounds: ${animalBird.song}".also { songTextView.text = it }
            "Alarm: ${animalBird.alarm}".also { alarmTextView.text = it }
//            discountImageView3.text = animalBird.discountLink
            discountImageView3.load(animalBird.discountLink) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.ic_animal)
            }
        }
    }

}