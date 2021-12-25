package com.example.hw_ViewModelAndNavigation.petShop

import androidx.lifecycle.ViewModel

class PetShopListViewModel : ViewModel() {

    private val repository = PetShopRepository()
    private var animals = repository.animals

    fun addKitty(name: String, bread: String) {
        repository.addKitty()
    }

    fun deleteAnimals(position: Int) {
       repository.deleteAnimals(position)
    }

    fun getAnimalsList() = animals
}

