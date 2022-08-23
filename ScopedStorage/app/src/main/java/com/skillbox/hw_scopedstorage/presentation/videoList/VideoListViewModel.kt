package com.skillbox.hw_scopedstorage.presentation.videoList

import android.app.Application
import android.app.RemoteAction
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skillbox.hw_scopedstorage.data.Video
import com.skillbox.hw_scopedstorage.utils.SingleLiveEvent

class VideoListViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val permissionsGrantedMutableLiveData = MutableLiveData(true)
    val permissionsGrantedLiveData: LiveData<Boolean>
        get() = permissionsGrantedMutableLiveData

    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    private val imagesMutableLiveData = MutableLiveData<List<Video>>()
    val imagesLiveData: LiveData<List<Video>>
        get() = imagesMutableLiveData

    private val recoverableActionMutableLiveData = MutableLiveData<RemoteAction>()
    val recoverableActionLiveData: LiveData<RemoteAction>
        get() = recoverableActionMutableLiveData

    private var isObservingStarted: Boolean = false
    private var pendingDeleteId: Long? = null

    fun updatePermissionState(isGranted: Boolean) {
        if (isGranted) {
            permissionsGranted()
        } else {
            permissionsDenied()
        }
    }

    fun permissionsGranted() {
//        loadImages()
        if (isObservingStarted.not()) {
//            videoRepository.observeImages { loadImages() }
            isObservingStarted = true
        }
        permissionsGrantedMutableLiveData.postValue(true)
    }

    fun permissionsDenied() {
        permissionsGrantedMutableLiveData.postValue(false)
    }

}