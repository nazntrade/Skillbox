package com.example.hw18_permissionsAndDate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.hw18_permissionsAndDate.databinding.FragmentMainDateLocationListBinding


class MainDateLocationListFragment : Fragment(R.layout.fragment_main_date_location_list) {

    lateinit var binding: FragmentMainDateLocationListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainDateLocationListBinding.bind(view)

        checkPermissionShowLocation()
    }

    private fun checkPermissionShowLocation() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (isLocationPermissionGranted) {
            binding.addLocationButton.isGone = false
            binding.infoTextView.isGone = false
            binding.infoTextView.text = getString(R.string.no_locations)
            showLocation()
        } else {
            binding.allowButton.isGone = false
            binding.infoTextView.isGone = false
            binding.infoTextView.text = getString(R.string.you_need_permission)
            requestLocationPermission()
        }
    }

    private fun showLocation() {
        binding.addLocationButton.setOnClickListener {
            binding.infoTextView.isGone = true
            binding.infoTextView.text = ""
        }
    }

    private fun requestLocationPermission() {
        binding.allowButton.setOnClickListener {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        }
//        checkPermissionShowLocation()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 4313
    }

}