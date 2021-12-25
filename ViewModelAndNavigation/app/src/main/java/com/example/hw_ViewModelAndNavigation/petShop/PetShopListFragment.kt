package com.example.hw_ViewModelAndNavigation.petShop

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_ViewModelAndNavigation.R
import com.example.hw_ViewModelAndNavigation.databinding.FragmentPetShopListBinding
import com.example.hw_ViewModelAndNavigation.inflate
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator

class PetShopListFragment : Fragment(R.layout.fragment_pet_shop_list) {

    private var petShopAdapter: PetShopAdapter? = null
    private val petShopListViewModel: PetShopListViewModel by viewModels()
    lateinit var binding: FragmentPetShopListBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetShopListBinding.bind(view)
        initList()

        binding.addFab.setOnClickListener {
            addNewKittyWithDialogWindow()
        }

        updatePetShopList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        petShopAdapter = null
    }

    private fun initList() {
        petShopAdapter = PetShopAdapter { position -> deleteAnimals(position) }
        with(binding.petList) {
            adapter = petShopAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = FlipInRightYAnimator()
        }
    }

    private fun deleteAnimals(position: Int) {
        petShopListViewModel.deleteAnimals(position)
        if (petShopListViewModel.getAnimalsList().isEmpty()) {
            binding.emptyTextView.isGone = false
            "List empty".also { binding.emptyTextView.text = it }
        }
        updatePetShopList()
    }

    private fun addNewKittyWithDialogWindow() {
        val view = binding.root.inflate(R.layout.dialog_add_kitty)
        val dialogNameTextView = view.findViewById<EditText>(R.id.dialogNameTextView)
        val dialogBreedTextView = view.findViewById<EditText>(R.id.dialogBreedTextView)
        val builder = AlertDialog.Builder(view.context)
        builder.setView(view)
        builder.setPositiveButton("Ok") { _, _ ->
            petShopListViewModel.addKitty(
                name = dialogNameTextView.text.toString(),
                bread = dialogBreedTextView.text.toString()
            )
            binding.petList.scrollToPosition(0)
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()

        updatePetShopList()
    }

    private fun updatePetShopList() {
        petShopAdapter?.items = petShopListViewModel.getAnimalsList()
    }
}