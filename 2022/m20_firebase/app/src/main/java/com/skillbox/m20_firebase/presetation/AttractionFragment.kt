package com.skillbox.m20_firebase.presetation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skillbox.m20_firebase.R
import com.skillbox.m20_firebase.databinding.FragmentAttractionBinding
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

    private var map: GoogleMap? = null
    private var locationListener: LocationSource.OnLocationChangedListener? = null

    private val locationCallback = object : LocationCallback() {
        @SuppressLint("MissingPermission")
        override fun onLocationResult(locResult: LocationResult) {
            locResult.lastLocation?.let { location ->
                locationListener?.onLocationChanged(location)
                val lat = location.latitude
                val lon = location.longitude
                moveAnimateCameraToPoint(lat, lon)
                getAttractions(lat, lon)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedClient = LocationServices.getFusedLocationProviderClient(
            requireActivity().applicationContext
        )
        getMap()
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
        ).setMinUpdateDistanceMeters(200f).build() // do this to order to reduce requests to server

        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun moveAnimateCameraToPoint(lat: Double, lon: Double) {
        Handler(Looper.getMainLooper()).postDelayed({ // Do this for smooth usability
            map?.animateCamera(
                CameraUpdateFactory
                    .newLatLngZoom(
                        LatLng(lat, lon),
                        14f
                    )
            )
        }, 1000)
    }

    private fun getAttractions(myLat: Double, myLon: Double) {
        viewModel.getAttractions(myLon, myLat)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.attractionsFlow.collect { attractions ->
                attractions?.forEach {
                    val dist = it.properties.dist
                    val lat = it.geometryModel.coordinates[1]
                    val lon = it.geometryModel.coordinates[0]
                    val title = it.properties.name
                    val attractionsCoordinates = LatLng(lat, lon)
                    map?.addMarker(
                        MarkerOptions()
                            .position(attractionsCoordinates)
                            .title(title)
                            .snippet("Distance: ${dist.toInt()}m")
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getMap(
    ) {
        val mapCallback = OnMapReadyCallback { googleMap ->
            map = googleMap
            with(map?.uiSettings) {
                this?.isZoomControlsEnabled = true
                this?.isMyLocationButtonEnabled = true
            }
            map?.mapType = GoogleMap.MAP_TYPE_HYBRID
            map?.isMyLocationEnabled = true
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