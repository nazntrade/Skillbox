package com.skillbox.coroutines

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment: Fragment(R.layout.fragment_menu) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        basicCoroutinesButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToBasicCoroutineFragment())
        }

        callbackToSuspendButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToCallbackToSuspendFragment())
        }

        errorCancellingButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToErrorCancelFragment())
        }

        androidCoroutines.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToCoroutineWithNetworking())
        }
    }

}