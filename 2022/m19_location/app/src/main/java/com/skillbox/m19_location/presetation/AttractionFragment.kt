package com.skillbox.m19_location.presetation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skillbox.m19_location.R
import com.skillbox.m19_location.databinding.FragmentAttractionBinding
import com.skillbox.m19_location.entity.Attractions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AttractionFragment :
    ViewBindingFragment<FragmentAttractionBinding>(FragmentAttractionBinding::inflate) {

    @Inject
    lateinit var attractionViewModelFactory: AttractionViewModelFactory

    private val viewModel: AttractionViewModel by viewModels {
        attractionViewModelFactory
    }

    private lateinit var fusedClient: FusedLocationProviderClient

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.all { it }) {
            startLocation()
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val myLat = locationResult.lastLocation!!.latitude
            val myLon = locationResult.lastLocation!!.longitude
            getAttractions(myLat, myLon)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedClient = LocationServices.getFusedLocationProviderClient(
            requireActivity().applicationContext
        )
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (REQUIRED_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocation()
        } else {
            launcher.launch(REQUIRED_PERMISSIONS)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocation() {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            5000
        ).build()

        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun getAttractions(myLat: Double, myLon: Double) {
        val myLocation = LatLng(myLat, myLon)
        viewModel.getAttractions(myLon, myLat)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.attractionsFlow.collect { attractions ->
                if (attractions != null) {
                    getMap(myLocation, attractions)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMap(myLocation: LatLng, attractions: List<Attractions>) {
        val mapCallback = OnMapReadyCallback { googleMap ->
            with(googleMap.uiSettings) {
                isZoomControlsEnabled = true
                isMyLocationButtonEnabled = true
            }

            googleMap.isMyLocationEnabled = true
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation))

            attractions.forEach {
                val dist = it.properties.dist
                val lat = it.geometryModel.coordinates[1]
                val lon = it.geometryModel.coordinates[0]
                val title = it.properties.name
                val attractionsCoordinates = LatLng(lat, lon)
                googleMap.addMarker(MarkerOptions()
                    .position(attractionsCoordinates)
                    .title(title)
                    .snippet("Distance: ${dist.toInt()}m")
                )
            }
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mapFragment?.getMapAsync(mapCallback)
        }
    }

    override fun onStop() {
        super.onStop()
        fusedClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private val REQUIRED_PERMISSIONS: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}