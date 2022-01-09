package com.example.hw_ViewModelAndNavigation.petShop

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PetShopListViewModel : ViewModel() {

    private val repository = PetShopRepository()

    val liveData: LiveData<List<Animal>> = repository.animals

    val newAnimal: Animal.Cat = repository.newAnimal

    private val showToastLiveData = SingleLiveEvent<Unit>()
    val showToastGet: LiveData<Unit>
        get() = showToastLiveData

    fun addAndUpdateListFun(newAnimal: Animal) {
        repository.addKitty(newAnimal)
        showToastLiveData.postValue(Unit)
    }

    fun deleteAnimals(position: Int) {
        repository.deleteAnimals(position )
    }
}

