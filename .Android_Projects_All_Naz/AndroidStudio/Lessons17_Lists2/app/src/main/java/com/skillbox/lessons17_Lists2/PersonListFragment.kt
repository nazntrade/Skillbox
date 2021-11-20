package com.skillbox.lessons17_Lists2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.skillbox.lessons17_Lists2.adapters.PersonAdapter
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlin.random.Random

class PersonListFragment : Fragment(R.layout.fragment_user_list) {

    private var persons = generatePersons(1000)

    private var personAdapter: PersonAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener { addUser() }
        personAdapter?.items = persons
    }

    private fun generatePersons(count: Int): List<Person> {
        val avatars = listOf(
            "https://cdn.pixabay.com/photo/2014/04/03/10/32/businessman-310819_1280.png",
            "https://png.pngtree.com/png-clipart/20190922/original/pngtree-business-male-user-avatar-vector-png-image_4774078.jpg",
            "https://png.pngtree.com/png-clipart/20190924/original/pngtree-female-user-avatars-flat-style-women-profession-vector-png-image_4822944.jpg",
            "https://cdn.pixabay.com/photo/2014/04/03/10/32/user-310807_1280.png"
        )

        val names = listOf(
            "Иван Петров",
            "Сергей Пупкин",
            "Мария Сидорова",
            "Елена Иванова"
        )

        val programmingLanguages = listOf(
            "Kotlin",
            "Java",
            "C++",
            "Python",
            "Javascript"
        )

        return (0..count).map {
            val id = it.toLong()
            val name = names.random()
            val avatar = avatars.random()
            val programmingLanguage = programmingLanguages.random()
            val isDeveloper = Random.nextBoolean()
            val age = Random.nextInt(15, 50)

            if (isDeveloper) {
                Person.Developer(
                    id = id,
                    name = name,
                    avatarLink = avatar,
                    age = age,
                    programmingLanguage = programmingLanguage
                )
            } else {
                Person.User(
                    id = id,
                    name = name,
                    avatarLink = avatar,
                    age = age
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        personAdapter = null
    }

    private fun initList() {
        personAdapter = PersonAdapter { position ->
            deletePerson(position)
        }

        val linearLayoutManager = LinearLayoutManager(requireContext())
        with(userList) {
            adapter = personAdapter
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            itemAnimator = ScaleInAnimator()
        }

        personAdapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (positionStart == 0 && positionStart == linearLayoutManager.findFirstCompletelyVisibleItemPosition()) {
                    linearLayoutManager.scrollToPosition(0)
                }
            }
        })
    }

    private fun deletePerson(position: Int) {
        persons = persons.filterIndexed { index, user -> index != position }
        personAdapter?.items = persons
    }

    private fun addUser() {
        val newUser = persons.random().let {
            when (it) {
                is Person.Developer -> it.copy(id = Random.nextLong())
                is Person.User -> it.copy(id = Random.nextLong())
            }
        }
        persons = listOf(newUser) + persons
        personAdapter?.items = persons
    }
}