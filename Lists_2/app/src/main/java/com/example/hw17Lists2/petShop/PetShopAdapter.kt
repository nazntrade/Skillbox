package com.example.hw17Lists2.petShop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.hw17Lists2.R
import com.example.hw17Lists2.databinding.ItemAnimalBinding
import com.example.hw17Lists2.databinding.ItemBirdBinding
import com.example.hw17Lists2.databinding.ItemDogBinding

class PetShopAdapter(
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var animals: List<Animal> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val bindingCat = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context))
        val bindingBird = ItemBirdBinding.inflate(LayoutInflater.from(parent.context))
        val bindingDog = ItemDogBinding.inflate(LayoutInflater.from(parent.context))

        return when (viewType) {
            TYPE_CAT -> CatHolder(parent.inflate(R.layout.item_animal), onItemClick)
            TYPE_DOG -> DogHolder(parent.inflate(R.layout.item_dog), onItemClick)
            TYPE_BIRD -> BirdHolder(parent.inflate(R.layout.item_bird), onItemClick)
            else -> error("Incorrect viewType: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (animals[position]) { //L.16.6 t.8.00
            is Animal.Cat -> TYPE_CAT
            is Animal.Dog -> TYPE_DOG
            is Animal.Bird -> TYPE_BIRD
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is DogHolder -> {
                val animalDog = animals[position].let { it as? Animal.Dog }
                    ?: error("Animal at position = $position is not a dog")
                holder.bindDog(animalDog)
            }

            is CatHolder -> {
                val animalCat = animals[position].let { it as? Animal.Cat }
                    ?: error("Animal at position = $position is not a cat")
                holder.bindCat(animalCat)
            }

            is BirdHolder -> {
                val animalBird = animals[position].let { it as? Animal.Bird }
                    ?: error("Animal at position = $position is not a bird")
                holder.bindBird(animalBird)
            }
            else -> error("Incorrect view holder = $holder")
        }
    }

    override fun getItemCount(): Int = animals.size

    fun updateAnimals(newAnimals: List<Animal>) {
        animals = newAnimals
    }

    abstract class BaseAnimalsHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private var bindingBaseAnimalsHolder = ItemAnimalBinding.bind(view)
        private val nameTextView = bindingBaseAnimalsHolder.nameTextView
        private val avatarImageView = bindingBaseAnimalsHolder.avatarImageView
        private val breedTextView = bindingBaseAnimalsHolder.breedTextView

        init {
            bindingBaseAnimalsHolder.root.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        protected fun bindMainInfo(name: String, avatarLink: String, breed: String) {
            "Name: $name".also { nameTextView.text = it }
            "Breed: $breed".also { breedTextView.text = it }
            avatarImageView.load(avatarLink) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.ic_animal)
                transformations(CircleCropTransformation())
            }
        }
    }

    class CatHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseAnimalsHolder(view, onItemClick) {
        private var bindingCatHolder = ItemAnimalBinding.bind(view)
        fun bindCat(animalCat: Animal.Cat) {
            bindMainInfo(animalCat.name, animalCat.avatarLink, animalCat.breed)
        }
    }

    class DogHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseAnimalsHolder(view, onItemClick) {
        private var bindingDogHolder = ItemDogBinding.bind(view)
        private val dogSkillTextView = bindingDogHolder.dogSkillTextView
        fun bindDog(animalDog: Animal.Dog) {
            bindMainInfo(animalDog.name, animalDog.avatarLink, animalDog.breed)
            "Skill: ${animalDog.skill}".also { dogSkillTextView.text = it }
        }
    }

    class BirdHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseAnimalsHolder(view, onItemClick) {
        private var bindingBirdHolder = ItemBirdBinding.bind(view)
        private val songTextView = bindingBirdHolder.songTextView
        private val alarmTextView = bindingBirdHolder.alarmTextView
        private val discountImageView3 = bindingBirdHolder.discountImageView3
        fun bindBird(animalBird: Animal.Bird) {
            bindMainInfo(animalBird.name, animalBird.avatarLink, animalBird.breed)
            "Sounds: ${animalBird.song}".also { songTextView.text = it }
            "Alarm: ${animalBird.alarm}".also { alarmTextView.text = it }
//            discountImageView3.text = animalBird.discountLink
            discountImageView3.load(animalBird.discountLink) {
                error(R.drawable.ic_404)
                placeholder(R.drawable.ic_animal)
            }
        }
    }

    companion object {
        private const val TYPE_CAT = 1
        private const val TYPE_BIRD = 2
        private const val TYPE_DOG = 3
    }
}