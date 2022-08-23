package com.skillbox.hw_scopedstorage.presentation.videoList

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skillbox.hw_scopedstorage.R
import com.skillbox.hw_scopedstorage.databinding.FragmentVideoListBinding
import com.skillbox.hw_scopedstorage.utils.ViewBindingFragment
import com.skillbox.hw_scopedstorage.utils.haveQ

//since this time binding only this and +file: ViewBindingFragment
class VideoListFragment :
    ViewBindingFragment<FragmentVideoListBinding>(FragmentVideoListBinding::inflate) {

    private val viewModel: VideoListViewModel by viewModels()
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPermissionResultListener()
//        initRecoverableActionListener()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()

        if (hasPermission().not()) {
            requestPermissions()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePermissionState(hasPermission())
    }

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

    private fun requestPermissions() {
        requestPermissionLauncher.launch(*PERMISSIONS.toTypedArray())
    }

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

    companion object {
        private val PERMISSIONS = listOfNotNull(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
                .takeIf { haveQ().not() }
        )
    }
}