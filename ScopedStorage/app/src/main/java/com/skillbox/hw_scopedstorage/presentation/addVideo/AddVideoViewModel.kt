package com.skillbox.hw_scopedstorage.presentation.addVideo

import android.app.Application
import android.net.Uri
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

    val sampleVideosViewModel
        get() = videosRepository.sampleVideosRepository

    private val saveSuccessSingleLiveEvent = SingleLiveEvent<Unit>()
    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessSingleLiveEvent

    private val loadingMutableLiveData = MutableLiveData(false)
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveVideo(name: String, url: String) {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(true)
            try {
                videosRepository.saveVideo(name, url)
                saveSuccessSingleLiveEvent.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t)
                toastSingleLiveEvent.postValue(R.string.video_add_error)
            } finally {
                loadingMutableLiveData.postValue(false)
            }
        }
    }

    fun saveVideoToCustomDir(url: String, uri: Uri) {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(true)
            try {
                videosRepository.saveVideoToCustomDir(url, uri)
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