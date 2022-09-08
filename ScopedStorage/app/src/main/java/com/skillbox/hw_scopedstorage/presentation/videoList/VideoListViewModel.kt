package com.skillbox.hw_scopedstorage.presentation.videoList

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.data.Video
import com.skillbox.hw_scopedstorage.data.VideosRepository
import com.skillbox.hw_scopedstorage.utils.SingleLiveEvent
import com.skillbox.hw_scopedstorage.utils.haveQ
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

    private val videoMutableLiveData = MutableLiveData<List<Video>>()
    val videoLiveData: LiveData<List<Video>>
        get() = videoMutableLiveData

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

    fun initExistedVideo(requireContext: Context) {
        viewModelScope.launch {
            try {
                videoRepository.initExistedVideo(requireContext)
            } catch (t: Throwable) {
                Timber.e(t, "main screen error")
            }
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
                videoMutableLiveData.postValue(video)
            } catch (t: Throwable) {
                Timber.e(t)
                videoMutableLiveData.postValue(emptyList())
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

    // ????
    fun confirmDelete() {
        pendingDeleteId?.let {
            deleteVideo(it)
        }
    }

    // ????
    fun declineDelete() {
        pendingDeleteId = null
    }

    override fun onCleared() {
        super.onCleared()
        videoRepository.unregisterObserver()
    }

}