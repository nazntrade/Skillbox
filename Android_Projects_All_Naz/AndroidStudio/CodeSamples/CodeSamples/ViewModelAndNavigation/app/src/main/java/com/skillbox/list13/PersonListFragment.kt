package com.skillbox.list13

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skillbox.list13.adapters.PersonAdapter
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlin.random.Random

class PersonListFragment: Fragment(R.layout.fragment_user_list) {

    private var personAdapter: PersonAdapter? = null

    private val personListViewModel: PersonListViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener { addUser() }
        observeViewModelState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        personAdapter = null
    }

    private fun initList() {
        personAdapter = PersonAdapter { id ->
            val action = PersonListFragmentDirections.actionPersonListFragmentToDetailsFragment(id)
            findNavController().navigate(action)
        }
        with(userList) {
            adapter = personAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = ScaleInAnimator()
        }
    }

    private fun deletePerson(position: Int) {
        personListViewModel.deletePerson(position)
    }

    private fun addUser() {
        personListViewModel.addPerson()
        userList.scrollToPosition(0)
    }

    private fun observeViewModelState() {
        personListViewModel.persons
            .observe(viewLifecycleOwner) { newPersons -> personAdapter?.items = newPersons }

        personListViewModel.showToast
            .observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), "Элемент добавлен", Toast.LENGTH_SHORT).show()
            }
    }
}