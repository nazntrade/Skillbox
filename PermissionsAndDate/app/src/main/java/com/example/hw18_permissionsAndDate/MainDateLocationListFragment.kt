package com.example.hw18_permissionsAndDate

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw18_permissionsAndDate.databinding.FragmentMainDateLocationListBinding
import com.example.hw18_permissionsAndDate.databinding.ItemDateLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.threeten.bp.*

class MainDateLocationListFragment : Fragment(R.layout.fragment_main_date_location_list) {

    private var dateLocationMessageAdapter: DateLocationMessageAdapter? = null
    private var dateLocationMessages: List<DateLocationMessage> = listOf()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var selectedMessageInstant: Instant? = null

    private lateinit var binding: FragmentMainDateLocationListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainDateLocationListBinding.bind(view)

        checkPermissionShowLocation()
        initList()
    }

    private fun initList() = with(binding.dateLocationList) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        dateLocationMessageAdapter =
            DateLocationMessageAdapter { position -> dateTimePicker(position) }
        adapter = dateLocationMessageAdapter
        layoutManager = LinearLayoutManager(requireContext())
        dateLocationMessageAdapter?.submitList(dateLocationMessages)
    }

    private fun dateTimePicker(position: Int) {
        val currentDateTime = LocalDateTime.now()
        DatePickerDialog(
            requireContext(), { _, year, month, dayOfMonth ->
                TimePickerDialog(
                    requireContext(), { _, hourOfDay, minute ->
                        val zonedDateTime =
                            LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                                .atZone(ZoneId.systemDefault())
                        selectedMessageInstant = zonedDateTime.toInstant()
                    },
                    currentDateTime.hour,
                    currentDateTime.minute,
                    true
                ).show()
            },
            currentDateTime.year,
            currentDateTime.month.value - 1,
            currentDateTime.dayOfMonth
        ).show()
        dateLocationMessages[position].createdAt = selectedMessageInstant!!

//         dateLocationMessageAdapter?.submitList(dateLocationMessages)

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
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val newDateLocationMessages = DateLocationMessage(
                            id = (9999..999999).random().toLong(),
                            image = "https://www.technipages.com/wp-content/uploads/2020/10/fix-google-maps-not-updating-location-600x341.png",
                            location = ("""
                                Lat = ${location.latitude}
                                Lng = ${location.longitude}
                                Alt = ${location.altitude}
                                Speed = ${location.speed}
                                Accuracy = ${location.accuracy}""").trimIndent(),
                            createdAt = selectedMessageInstant ?: Instant.now()
                        )
                        dateLocationMessages =
                            dateLocationMessages + listOf(newDateLocationMessages)
                        dateLocationMessageAdapter?.submitList(dateLocationMessages)
                    } else requestLocationPermission()
                }
                //обработка отмены
                .addOnCanceledListener {
                    toast("Запрос локации был отменен")
                }
                //обработка отмены
                .addOnFailureListener {
                    toast("Запрос локации завершился неудачно")
                }
        }
    }

}

