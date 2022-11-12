package ru.ktsstudio.roomdao.presentation.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_menu.*
import ru.ktsstudio.roomdao.R

class MenuFragment : Fragment(R.layout.fragment_menu) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usersButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToUsersFragment())
        }

        fkButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToFkFragment())
        }

        transactionIndicesButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToTransactionAndIndicesFragment())
        }
    }
}