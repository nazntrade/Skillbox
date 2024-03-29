package com.skillbox.hw_scopedstorage.presentation.videoList

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.data.Video
import com.skillbox.hw_scopedstorage.data.VideosRepository
import com.skillbox.hw_scopedstorage.utils.SingleLiveEvent
import com.skillbox.hw_scopedstorage.utils.haveQ
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class VideoListViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val videoRepository = VideosRepository(app)

    private var isObservingStarted: Boolean = false

    private var pendingDeleteId: Long? = null

    private val permissionsGrantedMutableLiveData = MutableLiveData(true)
    val permissionsGrantedLiveData: LiveData<Boolean>
        get() = permissionsGrantedMutableLiveData

    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    // I'm trying to use Flow
    //
    private val _videoFlow = MutableStateFlow<List<Video>?>(null)
    val videoFlow = _videoFlow.asStateFlow()
    //
    //

    private val recoverableActionMutableLiveData = MutableLiveData<RemoteAction>()
    val recoverableActionLiveData: LiveData<RemoteAction>
        get() = recoverableActionMutableLiveData

    fun updatePermissionState(isGranted: Boolean) {
        if (isGranted) {
            permissionsGranted()
        } else {
            permissionsDenied()
        }
    }

    fun startVideoObserver() {
        if (isObservingStarted.not()) {
            isObservingStarted = true
            videoRepository.observeVideo { loadVideo() }
        }
    }

    fun permissionsGranted() {
        loadVideo()
        permissionsGrantedMutableLiveData.postValue(true)
    }

    fun permissionsDenied() {
        permissionsGrantedMutableLiveData.postValue(false)
    }

    private fun loadVideo() {
        viewModelScope.launch {
            try {
                val video = videoRepository.getVideo()
                _videoFlow.value = video
            } catch (t: Throwable) {
                Timber.e(t)
                _videoFlow.value = emptyList()
                toastSingleLiveEvent.postValue(R.string.load_list_error)
            }
        }
    }

    fun deleteVideo(id: Long) {
        viewModelScope.launch {
            try {
                videoRepository.deleteVideo(id)
                pendingDeleteId = null
            } catch (t: Throwable) {
                Timber.e(t)
                if (haveQ() && t is RecoverableSecurityException) {
                    pendingDeleteId = id
                    recoverableActionMutableLiveData.postValue(t.userAction)
                } else {
                    toastSingleLiveEvent.postValue(R.string.video_delete_error)
                }
            }
        }
    }

    fun confirmDelete() {
        pendingDeleteId?.let {
            deleteVideo(it)
        }
    }

    fun declineDelete() {
        pendingDeleteId = null
    }

    override fun onCleared() {
        super.onCleared()
        videoRepository.unregisterObserver()
    }
}