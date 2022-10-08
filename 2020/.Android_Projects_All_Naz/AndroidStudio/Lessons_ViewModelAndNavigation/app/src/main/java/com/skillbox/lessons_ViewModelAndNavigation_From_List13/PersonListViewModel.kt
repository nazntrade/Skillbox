package com.skillbox.lessons_ViewModelAndNavigation_From_List13

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonListViewModel : ViewModel() {

    private val repository = PersonRepository()

    private val personLiveData = MutableLiveData<List<Person>>(
        repository.generatePersons(1000)
    )

    private val showToastLiveData = SingleLiveEvent<Unit>()

    val persons: LiveData<List<Person>>
        get() = personLiveData

    val showToast: LiveData<Unit>
        get() = showToastLiveData

    fun addPerson() {
        val updatedList = repository.createPerson(personLiveData.value.orEmpty())
        personLiveData.postValue(updatedList)
        showToastLiveData.postValue(Unit)
    }

    fun deletePerson(position: Int) {
        personLiveData.postValue(
            repository.deletePerson(personLiveData.value.orEmpty(), position)
        )
    }
}