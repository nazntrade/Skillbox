package com.skillbox.lessons18_PermissionsAndDate

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.skillbox.lessons18_PermissionsAndDate.R
import kotlinx.android.synthetic.main.fragment_dangerous_permission.*

class DangerousPermissionFragment : Fragment(R.layout.fragment_dangerous_permission) {

    //заранее пропишем диалог для сохранения в поле (х.з. что это значит)
    private var rationaleDialog: AlertDialog? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCurrentLocationButton.setOnClickListener {
            showCurrentLocationWithPermissionCheck()
        }
    }

    //проверка есть ли разрешение. !!!Важно делать проверку перед каждым действием
    private fun showCurrentLocationWithPermissionCheck() {
        val isLocationPermissionGranted = ActivityCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        //если резреш есть то...
        if (isLocationPermissionGranted) {
            showLocationInfo()
            //если нет то...
        } else {
            val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (needRationale) {
                showLocationRationaleDialog()
            } else {
                requestLocationPermission()
            }
        }
    }

    // узнать что выбрал пользователь
    //это работает, даже если пользователь отберет разрешения прямо в момент работы приложения
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Опять проверяем, если (все) разрешение есть, то ...
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            showLocationInfo()
        } else {
            //если разрешений нет, то сначала проверим, нужно ли объяснить пользователю, зачем мы запрашиваем это разрешение
            val needRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            //если объяснение нужно, отобразим диалог с объяснением. Это важно! Ведь это может быть не совсем очевидно.
            if (needRationale) {
                showLocationRationaleDialog()
            }
        }
    }

    private fun showLocationInfo() {
        // это работает в google play service. он отвечает за все сервисы и получение локации тоже
        LocationServices.getFusedLocationProviderClient(requireContext())//студия рекомендует добавить проверку пермишена. Спец.тут не делаю, чтобы оставить код, как в уроке.
            .lastLocation //получ.посл локацию кот сохранил сервис(не текущую)
            //add выполнится при удачном выполнении. когда будет получена посл.локация
            .addOnSuccessListener {
                it?.let {
                    positionTextView.text = """
                        Lat = ${it.latitude}
                        Lng = ${it.longitude}
                        Alt = ${it.altitude}
                        Speed = ${it.speed}
                        Accuracy = ${it.accuracy}
                    """.trimIndent()
                } ?: toast("Локация отсутствует")
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

    private fun showLocationRationaleDialog() {
        rationaleDialog = AlertDialog.Builder(requireContext())
            .setMessage("Необходимо одобрение разрешения для отображения информации по локации")
            .setPositiveButton(
                "OK",
                { _, _ -> requestLocationPermission() })//если пользователь все понял, то по кн.ОК запросим пермишн
            .setNegativeButton("Отмена", null)
            .show()
    }

    //не забываем закрывать и обновлять ссылку на диалог при уничтожении View чтоб не было утечек памяти
    override fun onDestroyView() {
        super.onDestroyView()
        rationaleDialog?.dismiss()
        rationaleDialog = null
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }

    //просто интовое значение для определения на какой именно запрос пришло разрешение
    companion object {
        private const val PERMISSION_REQUEST_CODE = 4313
    }
}