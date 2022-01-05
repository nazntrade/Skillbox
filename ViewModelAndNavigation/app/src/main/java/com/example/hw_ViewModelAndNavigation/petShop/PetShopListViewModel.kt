package com.example.hw_ViewModelAndNavigation.petShop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PetShopListViewModel : ViewModel() {

    private val repository = PetShopRepository()

    //чтобы LiveData нельзя было изменить изВне но можно было получить ее значение
    private val animalsLiveData: MutableLiveData<List<Animal>> = MutableLiveData(repository.animals)

    //явно указываю НЕизменяемую и через GET можно получить значение animalsLiveData изВне
    val animalsLiveDataGet: LiveData<List<Animal>>
        get() = animalsLiveData

    val newAnimals: Animal.Cat = repository.addKitty()

    // если подписчик получил уведомление, то это оповещение больше не кому не будет доставлено
    // для этого поменям private val showToastLiveData = MutableLiveData<Unit>()
    // на...    SingleLiveEvent    который мы добавили из GitHab (я взял из lessons_ViewModel)
    private val showToastLiveData = SingleLiveEvent<Unit>()
    val showToastGet: LiveData<Unit>
        get() = showToastLiveData

    fun addAndUpdateListFun(newAnimals: Animal): List<Animal> {
        val updateList = listOf(newAnimals) + animalsLiveData.value.orEmpty()
        animalsLiveData.postValue(updateList)
        showToastLiveData.postValue(Unit)
        return updateList
    }

    fun deleteAnimals(position: Int) {
        animalsLiveData.postValue(
            repository.deleteAnimals(animalsLiveData.value.orEmpty(), position)
        )
    }
}

