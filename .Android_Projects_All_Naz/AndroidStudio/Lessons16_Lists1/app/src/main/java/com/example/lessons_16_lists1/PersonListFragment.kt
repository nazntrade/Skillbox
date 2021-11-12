package com.example.lessons_16_lists1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lessons_16_lists1.databinding.FragmentUserListBinding

class PersonListFragment : Fragment(R.layout.fragment_user_list) {

    private var persons = listOf(
        Person.Developer(
            name = "Иван Петров",
            avatarLink = "https://i.imgur.com/geX7lBe.jpg",
            age = 25,
            programmingLanguage = "Java"
        ),
        Person.User(
            name = "Петр Иванов",
            avatarLink = "https://img0.liveinternet.ru/images/attach/c/2//64/883/64883121_1286237281_TVDthevampirediaries100941571024768.jpg",
            age = 30,
        ),
        Person.Developer(
            name = "Елена Сергеевна",
            avatarLink = "https://art-assorty.ru/uploads/posts/2018-03/1520934636_10.jpg",
            age = 18,
            programmingLanguage = "Kotlin"
        ),
        Person.User(
            name = "Анна Александрова",
            avatarLink = "https://www.ridus.ru/images/2019/10/15/992163/in_article_17e12c78ef.jpg",
            age = 30,
        )
    )

    private var personAdapter: PersonAdapter? = null

    private lateinit var binding: FragmentUserListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserListBinding.bind(view)
        initList()

        binding.addFab.setOnClickListener {
            addUser()
        }

        personAdapter?.updatePersons(persons)
        personAdapter?.notifyDataSetChanged()
//        userAdapter?.notifyItemRangeInserted(0, users.size)
    }

    //обязательно нужно очищать. Лучше пользоваться AutoClearedValue
    override fun onDestroyView() {
        super.onDestroyView()
        personAdapter = null
    }

    private fun initList() {
        personAdapter = PersonAdapter { position -> deletePerson(position) }
        with(binding.userList) {
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
        binding.userList.scrollToPosition(0)
    }
}
