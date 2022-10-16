package com.skillbox.lists12

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment: Fragment(R.layout.fragment_user_list) {

    private var users = listOf(
        User(
            name = "Иван Петров",
            avatarLink = "https://cdn.pixabay.com/photo/2014/04/03/10/32/businessman-310819_1280.png",
            age = 25,
            isDeveloper = true
        ),
        User(
            name = "Петр Иванов",
            avatarLink = "https://png.pngtree.com/png-clipart/20190922/original/pngtree-business-male-user-avatar-vector-png-image_4774078.jpg",
            age = 30,
            isDeveloper = false
        ),
        User(
            name = "Елена Сергеевна",
            avatarLink = "https://png.pngtree.com/png-clipart/20190924/original/pngtree-female-user-avatars-flat-style-women-profession-vector-png-image_4822944.jpg",
            age = 18,
            isDeveloper = true
        ),
        User(
            name = "Анна Александрова",
            avatarLink = "https://cdn.pixabay.com/photo/2014/04/03/10/32/user-310807_1280.png",
            age = 30,
            isDeveloper = false
        )
    )

    private var userAdapter: UserAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        addFab.setOnClickListener { addUser() }
        userAdapter?.updateUsers(users)
        userAdapter?.notifyDataSetChanged()
//        userAdapter?.notifyItemRangeInserted(0, users.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        userAdapter = null
    }

    private fun initList() {
        userAdapter = UserAdapter { position -> deleteUser(position) }
        with(userList) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun deleteUser(position: Int) {
        users = users.filterIndexed { index, user -> index != position }
        userAdapter?.updateUsers(users)
        userAdapter?.notifyItemRemoved(position)
    }

    private fun addUser() {
        val newUser = users.random()
        users = listOf(newUser) + users
        userAdapter?.updateUsers(users)
        userAdapter?.notifyItemInserted(0)
        userList.scrollToPosition(0)
    }
}