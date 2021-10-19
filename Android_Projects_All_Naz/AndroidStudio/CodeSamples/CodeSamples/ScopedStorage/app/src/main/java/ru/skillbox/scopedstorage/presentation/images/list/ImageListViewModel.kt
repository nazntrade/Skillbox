package ru.skillbox.scopedstorage.presentation.images.list

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.scopedstorage.R
import ru.skillbox.scopedstorage.data.Image
import ru.skillbox.scopedstorage.data.ImagesRepository
import ru.skillbox.scopedstorage.utils.SingleLiveEvent
import ru.skillbox.scopedstorage.utils.haveQ
import timber.log.Timber

class ImageListViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val imagesRepository = ImagesRepository(app)

    private val permissionsGrantedMutableLiveData = MutableLiveData(true)
    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    private val imagesMutableLiveData = MutableLiveData<List<Image>>()
    private val recoverableActionMutableLiveData = MutableLiveData<RemoteAction>()

    private var isObservingStarted: Boolean = false
    private var pendingDeleteId: Long? = null

    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    val imagesLiveData: LiveData<List<Image>>
        get() = imagesMutableLiveData

    val permissionsGrantedLiveData: LiveData<Boolean>
        get() = permissionsGrantedMutableLiveData

    val recoverableActionLiveData: LiveData<RemoteAction>
        get() = recoverableActionMutableLiveData

    override fun onCleared() {
        super.onCleared()
        imagesRepository.unregisterObserver()
    }

    fun updatePermissionState(isGranted: Boolean) {
        if(isGranted) {
            permissionsGranted()
        } else {
            permissionsDenied()
        }
    }

    fun permissionsGranted() {
        loadImages()
        if(isObservingStarted.not()) {
            imagesRepository.observeImages { loadImages() }
            isObservingStarted = true
        }
        permissionsGrantedMutableLiveData.postValue(true)
    }

    fun permissionsDenied() {
        permissionsGrantedMutableLiveData.postValue(false)
    }

    fun deleteImage(id: Long) {
        viewModelScope.launch {
            try {
                imagesRepository.deleteImage(id)
                pendingDeleteId = null
            } catch (t: Throwable) {
                Timber.e(t)
                if(haveQ() && t is RecoverableSecurityException) {
                    pendingDeleteId = id
                    recoverableActionMutableLiveData.postValue(t.userAction)
                } else {
                    toastSingleLiveEvent.postValue(R.string.image_list_delete_error)
                }
            }
        }
    }

    fun confirmDelete() {
        pendingDeleteId?.let {
            deleteImage(it)
        }
    }

    fun declineDelete() {
        pendingDeleteId = null
    }

    private fun loadImages() {
        viewModelScope.launch {
            try {
                val images = imagesRepository.getImages()
                imagesMutableLiveData.postValue(images)
            } catch (t: Throwable) {
                Timber.e(t)
                imagesMutableLiveData.postValue(emptyList())
                toastSingleLiveEvent.postValue(R.string.image_list_error)
            }
        }
    }
}