package com.example.hw_ViewModelAndNavigation.petShop

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_ViewModelAndNavigation.R
import com.example.hw_ViewModelAndNavigation.databinding.FragmentPetShopListBinding
import com.example.hw_ViewModelAndNavigation.inflate
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator

class PetShopListFragment : Fragment(R.layout.fragment_pet_shop_list) {

    private var petShopAdapter: PetShopAdapter? = null
    private val viewModel: PetShopListViewModel by viewModels()
    private lateinit var binding: FragmentPetShopListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetShopListBinding.bind(view)
        initList()
        binding.addFab.setOnClickListener {
            addNewKittyWithDialogWindow()
        }
        observeViewModelState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        petShopAdapter = null
    }

    private fun initList() {
        petShopAdapter = PetShopAdapter(
            { id -> navigate(id) },
            { position -> deleteAnimals(position) })
        with(binding.petList) {
            adapter = petShopAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = FlipInRightYAnimator()
        }
    }

    private fun deleteAnimals(position: Int) {
        viewModel.deleteAnimals(position)
    }

    private fun navigate(id: Long) {
        val action = PetShopListFragmentDirections.actionPetShopListFragmentToDetailsFragment(id)
        findNavController().navigate(action)
    }

    private fun addNewKittyWithDialogWindow() {
        val view = binding.root.inflate(R.layout.dialog_add_kitty)
        val dialogNameTextView = view.findViewById<EditText>(R.id.dialogNameTextView)
        val dialogBreedTextView = view.findViewById<EditText>(R.id.dialogBreedTextView)
        val builder = AlertDialog.Builder(view.context)
        builder.setView(view)
        builder.setPositiveButton("Ok") { _, _ ->
            val newCat = Animal.Cat(
                id = (9999..112121).random().toLong(),
                name = dialogNameTextView.text.toString(),
                breed = dialogBreedTextView.text.toString(),
                avatarLink = viewModel.getRandomPhoto
            )
            viewModel.addAndUpdateListFun(newCat)
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun observeViewModelState() {
        viewModel.liveData
            //разобрать обзерв. Раньше иф был в ф-ии делейт и не работал. как только перенес в обзерв(по рекоменд.преподавателя)все заработало!!!
            .observe(viewLifecycleOwner) { newAnimals ->
                petShopAdapter?.items = newAnimals
                petShopAdapter?.setItems(newAnimals) {
                    binding.petList.scrollToPosition(0)
                }
                if (newAnimals.isEmpty()) {
                    binding.emptyTextView.isGone = false
                    "List empty".also { binding.emptyTextView.text = it }
                }
            }
        viewModel.showToastGet
            .observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Kitty added", Toast.LENGTH_SHORT).show()
            }
    }
}
