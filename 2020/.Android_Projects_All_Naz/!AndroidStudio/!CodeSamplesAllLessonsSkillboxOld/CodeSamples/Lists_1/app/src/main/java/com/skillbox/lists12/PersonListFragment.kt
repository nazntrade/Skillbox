package com.skillbox.lists12

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_list.*

class PersonListFragment: Fragment(R.layout.fragment_user_list) {

    private var persons = listOf(
        Person.Developer(
            name = "Иван Петров",
            avatarLink = "https://cdn.pixabay.com/photo/2014/04/03/10/32/businessman-310819_1280.png",
            age = 25,
            programmingLanguage = "Java"
        ),
        Person.User(
            name = "Петр Иванов",
            avatarLink = "https://png.pngtree.com/png-clipart/20190922/original/pngtree-business-male-user-avatar-vector-png-image_4774078.jpg",
            age = 30
        ),
        Person.Developer(
            name = "Елена Сергеевна",
            avatarLink = "https://png.pngtree.com/png-clipart/20190924/original/pngtree-female-user-avatars-flat-style-women-profession-vector-png-image_4822944.jpg",
            age = 18,
            programmingLanguage = "Kotlin"
        ),
        Person.User(
            name = "Анна Александрова",
            avatarLink = "https://cdn.pixabay.com/photo/2014/04/03/10/32/user-310807_1280.png",
            age = 30
        )
    )

    private var personAdapter: PersonAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener { addUser() }
        personAdapter?.updatePersons(persons)
        personAdapter?.notifyDataSetChanged()
//        userAdapter?.notifyItemRangeInserted(0, users.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        personAdapter = null
    }

    private fun initList() {
        personAdapter = PersonAdapter { position -> deletePerson(position) }
        with(userList) {
            adapter = personAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun deletePerson(position: Int) {
        persons = persons.filterIndexed { index, user -> index != position }
        personAdapter?.updatePersons(persons)
        personAdapter?.notifyItemRemoved(position)
    }

    private fun addUser() {
        val newUser = persons.random()
        persons = listOf(newUser) + persons
        personAdapter?.updatePersons(persons)
        personAdapter?.notifyItemInserted(0)
        userList.scrollToPosition(0)
    }
}