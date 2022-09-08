package com.skillbox.hw_scopedstorage.presentation.videoList

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.RemoteAction
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.databinding.FragmentVideoListBinding
import com.skillbox.hw_scopedstorage.presentation.videoList.videoListAdapter.VideoAdapter
import com.skillbox.hw_scopedstorage.utils.ViewBindingFragment
import com.skillbox.hw_scopedstorage.utils.autoCleared
import com.skillbox.hw_scopedstorage.utils.haveQ
import com.skillbox.hw_scopedstorage.utils.toast

//since this time binding only this. and + file: ViewBindingFragment
class VideoListFragment :
    ViewBindingFragment<FragmentVideoListBinding>(FragmentVideoListBinding::inflate) {

    private val viewModel: VideoListViewModel by viewModels()
    private var videoAdapter: VideoAdapter by autoCleared()

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>> //1 PERMISSIONS
    private lateinit var recoverableActionLauncher: ActivityResultLauncher<IntentSenderRequest> //// What is it?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPermissionResultListener() //1 PERMISSIONS
        initRecoverableActionListener() // what is it?

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startVideoObserver()
        initToolBar()
        //1 PERMISSIONS
        if (hasPermission().not()) {
            requestPermissions()
        }
        toast("Please wait a minute until video is downloading")
        initList()
        initCallbacks()
        bindViewModel()
        viewModel.initExistedVideo(requireContext())
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePermissionState(hasPermission())//1 PERMISSIONS
    }

    private fun initCallbacks() {
        binding.addVideoButton.setOnClickListener {
            findNavController().navigate(R.id.action_videoListFragment_to_addVideoDialogFragment)
        }
        binding.grantPermissionButton.setOnClickListener {
            requestPermissions()
        }
    }

    private fun initList() {
        videoAdapter = VideoAdapter(viewModel::deleteVideo)
        with(binding.videoList) {
            setHasFixedSize(true)
            adapter = videoAdapter
        }
    }

    private fun bindViewModel() {
        viewModel.toastLiveData.observe(viewLifecycleOwner) { toast(it) }
        viewModel.videoLiveData.observe(viewLifecycleOwner) { videoAdapter.items = it }
        viewModel.permissionsGrantedLiveData.observe(viewLifecycleOwner, ::updatePermissionUi)// ???
        viewModel.recoverableActionLiveData.observe(viewLifecycleOwner, ::handleRecoverableAction)

    }

    //????
    private fun updatePermissionUi(isGranted: Boolean) {
        binding.grantPermissionButton.isVisible = isGranted.not()
        binding.addVideoButton.isVisible = isGranted
        binding.videoList.isVisible = isGranted
    }

    //1 PERMISSIONS
    private fun initPermissionResultListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionToGrantedMap: Map<String, Boolean> ->
            if (permissionToGrantedMap.values.all { it }) {
                viewModel.permissionsGranted()
            } else {
                viewModel.permissionsDenied()
            }
        }
    }

    // ????
    private fun initRecoverableActionListener() {
        recoverableActionLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { activityResult ->
            val isConfirmed = activityResult.resultCode == Activity.RESULT_OK
            if (isConfirmed) {
                viewModel.confirmDelete()
            } else {
                viewModel.declineDelete()
            }
        }
    }

    //1 PERMISSIONS
    private fun requestPermissions() {
        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
    }

    //1 PERMISSIONS
    private fun hasPermission(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    //including statusBar
    private fun initToolBar() {
        binding.appBar.toolBar.title = getString(R.string.toolBarTitle)
    }

    // ????
    @SuppressLint("NewApi")
    private fun handleRecoverableAction(action: RemoteAction) {
        val request = IntentSenderRequest.Builder(action.actionIntent.intentSender)
            .build()
        recoverableActionLauncher.launch(request)
    }

    //1 PERMISSIONS
    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() }
        )
    }
}