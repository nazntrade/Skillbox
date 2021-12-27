package com.example.hw_ViewModelAndNavigation.petShop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PetShopListViewModel : ViewModel() {

    private val repository = PetShopRepository()
    val animalsLiveData = MutableLiveData(repository.animals)

    val newAnimals: Animal.Cat = repository.addKitty()
    fun addAndUpdateListFun(): List<Animal> {
        val updateList = listOf(newAnimals) + animalsLiveData.value.orEmpty()
        animalsLiveData.postValue(updateList)
        return updateList
    }

//    fun addKitty(): Animal.Cat {
//
//        return newAnimals
//    }

    fun deleteAnimals(position: Int) {
        animalsLiveData.postValue(
            repository.deleteAnimals(animalsLiveData.value.orEmpty(), position)
        )
    }
}

