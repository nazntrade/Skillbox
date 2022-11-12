package ru.skillbox.flow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.flow.databinding.FragmentMenuBinding

class MenuFragment: Fragment(R.layout.fragment_menu) {

    private val binding: FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.flowBasicButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToFlowBasicFragment())
        }
        binding.flowOperatorsButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToFlowOperatorsFragment())
        }
    }

}