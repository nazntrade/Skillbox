package com.skillbox.hw_scopedstorage.presentation.addVideo

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.data.VideosRepository
import com.skillbox.hw_scopedstorage.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class AddVideoViewModel(app: Application) : AndroidViewModel(app) {

    private val videosRepository = VideosRepository(app)

    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    private val saveSuccessSingleLiveEvent = SingleLiveEvent<Unit>()
    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessSingleLiveEvent

    private val loadingMutableLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    fun saveVideo(requireContext: Context, name: String, url: String) {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(true)
            try {
                videosRepository.saveVideo(requireContext, name, url)
                saveSuccessSingleLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t)
                toastSingleLiveEvent.postValue(R.string.video_add_error)
            } finally {
                loadingMutableLiveData.postValue(false)
            }
        }
    }
}