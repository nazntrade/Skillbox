package com.example.hw_ViewModelAndNavigation.petShop

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PetShopListViewModel : ViewModel() {

    private val repository = PetShopRepository()

    val liveData: LiveData<List<Animal>> = repository.animals

    val getRandomPhoto
        get() = repository.getRandomPhotoKitty()

    private val showToastLiveData = SingleLiveEvent<Unit>()
    val showToastGet: LiveData<Unit>
        get() = showToastLiveData

    fun addAndUpdateListFun(newCat: Animal) {
        repository.addKitty(newCat)
        showToastLiveData.postValue(Unit)
    }

    fun deleteAnimals(position: Int) {
        repository.deleteAnimals(position)
    }
}

