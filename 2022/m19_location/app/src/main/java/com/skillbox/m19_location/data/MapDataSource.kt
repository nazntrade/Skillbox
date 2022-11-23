package com.skillbox.m19_location.data

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class MapDataSource @Inject constructor() {
    suspend fun loadMap(myLat: Double, myLon: Double): OnMapReadyCallback {

        val mapCallback = OnMapReadyCallback { googleMap ->
            val myPosition = LatLng(
                myLat,
                myLon
            )
            googleMap.addMarker(MarkerOptions().position(myPosition).title("You"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition))
        }
        return mapCallback
    }
}