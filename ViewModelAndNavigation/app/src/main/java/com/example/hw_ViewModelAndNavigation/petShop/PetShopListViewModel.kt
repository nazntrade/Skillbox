package com.example.hw_ViewModelAndNavigation.petShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PetShopListViewModel : ViewModel() {

    private val repository = PetShopRepository()
    val animalsLiveData = MutableLiveData(repository.animals)

    fun addKitty(name: String, bread: String): List<Animal> {
        val newAnimals = repository.addKitty()
        val updateList = listOf(newAnimals) + animalsLiveData.value.orEmpty()
        animalsLiveData.postValue(updateList)
        return updateList
    }

    fun deleteAnimals(position: Int) {
        animalsLiveData.postValue(
            repository.deleteAnimals(animalsLiveData.value.orEmpty(),position)
        )
    }
}

