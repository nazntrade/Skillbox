package com.skillbox.m19_location.data

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skillbox.m19_location.entity.Attractions
import javax.inject.Inject

class MapDataSource @Inject constructor() {
    suspend fun loadMap(myCoordinates: LatLng, attractions: List<Attractions>): OnMapReadyCallback {

        val mapCallback = OnMapReadyCallback { googleMap ->
            googleMap.addMarker(MarkerOptions().position(myCoordinates).title("You"))
            googleMap
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates))

            attractions.forEach {
                val lat = it.geometryModel.coordinates[1]
                val lon = it.geometryModel.coordinates[0]
                val title = it.properties.name
                val attractionsCoordinates = LatLng(lat, lon)
                googleMap.addMarker(MarkerOptions().position(attractionsCoordinates).title(title))
            }
        }
        return mapCallback
    }
}