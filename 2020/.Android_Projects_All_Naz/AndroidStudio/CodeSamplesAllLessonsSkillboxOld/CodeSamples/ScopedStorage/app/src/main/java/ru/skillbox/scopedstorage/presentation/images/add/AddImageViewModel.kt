package ru.skillbox.scopedstorage.presentation.images.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.scopedstorage.R
import ru.skillbox.scopedstorage.data.ImagesRepository
import ru.skillbox.scopedstorage.utils.SingleLiveEvent
import timber.log.Timber

class AddImageViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val imagesRepository = ImagesRepository(app)

    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    private val saveSuccessSingleLiveEvent = SingleLiveEvent<Unit>()
    private val loadingMutableLiveData = MutableLiveData<Boolean>(false)

    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessSingleLiveEvent

    fun saveImage(name: String, url: String) {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(true)
            try {
                imagesRepository.saveImage(name, url)
                saveSuccessSingleLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t)
                toastSingleLiveEvent.postValue(R.string.image_add_error)
            } finally {
                loadingMutableLiveData.postValue(false)
            }
        }
    }
}