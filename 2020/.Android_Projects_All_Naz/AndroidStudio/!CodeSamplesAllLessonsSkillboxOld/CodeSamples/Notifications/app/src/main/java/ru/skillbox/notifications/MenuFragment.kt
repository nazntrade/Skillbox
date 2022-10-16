package ru.skillbox.notifications

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.notifications.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding: FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    private val receiver = BatteryBroadcastReceiver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notificationButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToNotificationsFragment())
        }
        binding.firebaseButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToFirebaseFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_LOW))
        requireContext().registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_OKAY))
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
    }

}