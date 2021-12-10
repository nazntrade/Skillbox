package com.example.hw18_permissionsAndDate

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw18_permissionsAndDate.databinding.FragmentMainDateLocationListBinding
import com.google.android.gms.location.LocationServices
import org.threeten.bp.*


class MainDateLocationListFragment : Fragment(R.layout.fragment_main_date_location_list) {

    private var dateLocationMessageAdapter: DateLocationMessageAdapter? = null
    private var dateLocationMessages: List<DateLocationMessage> = listOf()


    lateinit var binding: FragmentMainDateLocationListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainDateLocationListBinding.bind(view)

        checkPermissionShowLocation()
        initList()
    }

    private fun initList() = with(binding.dateLocationList) {
        dateLocationMessageAdapter = DateLocationMessageAdapter()
        adapter = dateLocationMessageAdapter
        layoutManager = LinearLayoutManager(requireContext())
        dateLocationMessageAdapter?.submitList(dateLocationMessages)
    }

    private fun checkPermissionShowLocation() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (isLocationPermissionGranted) {
            showButtonAndTextLocation()
        } else {
            requestLocationPermission()
        }
    }

    //проверяем, что выбрал пользователь и в зависимости от выбора продолжаем ...
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            showButtonAndTextLocation()
        } else requestLocationPermission()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 4313
    }

    private fun showButtonAndTextLocation() {
        binding.allowButton.isGone = true
        binding.addLocationButton.isGone = false
        binding.infoTextView.isGone = false
        binding.infoTextView.text = getString(R.string.no_locations)
        binding.addLocationButton.setOnClickListener {
            binding.infoTextView.isGone = true
            binding.infoTextView.text = ""
            showMessageLocationAndDateInfo()
        }
    }

    private fun requestLocationPermission() {
        binding.allowButton.isGone = false
        binding.infoTextView.isGone = false
        binding.infoTextView.text = getString(R.string.you_need_permission)
        binding.allowButton.setOnClickListener {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun showMessageLocationAndDateInfo() {
        val currentDateTime = LocalDateTime.now()

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val newDateLocationMessages = DateLocationMessage(
                id = (9999..999999).random().toLong(),
                image = "https://www.technipages.com/wp-content/uploads/2020/10/fix-google-maps-not-updating-location-600x341.png",
                location = LocationServices.getFusedLocationProviderClient(requireContext())
                    .lastLocation
                    .addOnSuccessListener {
                        it?.let {
                            """
                              Lat = ${it.latitude}
                              Lng = ${it.longitude}
                              Alt = ${it.altitude}
                              Speed = ${it.speed}
                              Accuracy = ${it.accuracy}
                          """.trimIndent()
                        }
                    }
                    //                обработка отмены
                    .addOnCanceledListener {
                        toast("Запрос локации был отменен")
                    }
                    //обработка отмены
                    .addOnFailureListener {
                        toast("Запрос локации завершился неудачно")
                    }.toString()
                ,
                createdAt = currentDateTime
            )
            dateLocationMessages = dateLocationMessages + listOf(newDateLocationMessages)
            dateLocationMessageAdapter?.submitList(dateLocationMessages)

        }
        else requestLocationPermission()


    }

}



