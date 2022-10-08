package com.example.lessons_16_lists1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lessons_16_lists1.databinding.FragmentUserListBinding
import java.text.ParsePosition

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private var users = listOf(
        User(
            name = "Иван Петров",
            avatarLink = "https://i.imgur.com/geX7lBe.jpg",
            age = 25,
            isDeveloper = true
        ),
        User(
            name = "Петр Иванов",
            avatarLink = "https://img0.liveinternet.ru/images/attach/c/2//64/883/64883121_1286237281_TVDthevampirediaries100941571024768.jpg",
            age = 30,
            isDeveloper = false
        ),
        User(
            name = "Елена Сергеевна",
            avatarLink = "https://art-assorty.ru/uploads/posts/2018-03/1520934636_10.jpg",
            age = 18,
            isDeveloper = true
        ),
        User(
            name = "Анна Александрова",
            avatarLink = "https://www.ridus.ru/images/2019/10/15/992163/in_article_17e12c78ef.jpg",
            age = 30,
            isDeveloper = false
        )
    )

    private var userAdapter: UserAdapter? = null

    private lateinit var binding: FragmentUserListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserListBinding.bind(view)
        initList()

        binding.addFab.setOnClickListener {
            addUser()
        }

        userAdapter?.updateUsers(users)
        userAdapter?.notifyDataSetChanged()
//        userAdapter?.notifyItemRangeInserted(0, users.size)
    }

    //обязательно нужно очищать. Лучше пользоваться AutoClearedValue
    override fun onDestroyView() {
        super.onDestroyView()
        userAdapter = null
    }

    private fun initList() {
        userAdapter = UserAdapter { position -> deleteUser(position) }
        with(binding.userList) {
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
        binding.userList.scrollToPosition(0)
    }
}
