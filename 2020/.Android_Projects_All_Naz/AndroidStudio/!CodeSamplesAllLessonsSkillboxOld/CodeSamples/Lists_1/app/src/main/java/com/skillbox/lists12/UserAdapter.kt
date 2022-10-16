package com.skillbox.lists12

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(
    private val onItemClicked: (position: Int) -> Unit
): RecyclerView.Adapter<UserAdapter.Holder>() {

    private var users: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.d("UserAdapter", "onCreateViewHolder")
        return Holder(parent.inflate(R.layout.item_user), onItemClicked)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d("UserAdapter", "onBindViewHolder|position=$position")
        val user = users[position]
        holder.bind(user)
    }

    fun updateUsers(newUsers: List<User>) {
        users = newUsers
    }

    class Holder(
        view: View,
        onItemClicked: (position: Int) -> Unit
    ): RecyclerView.ViewHolder(view) {

        private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        private val ageTextView: TextView = view.findViewById(R.id.ageTextView)
        private val developerTextView: TextView = view.findViewById(R.id.developerTextView)
        private val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

        init {
            view.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(user: User) {
            nameTextView.text = user.name
            ageTextView.text = "Возраст = ${user.age}"
            developerTextView.visibility = if(user.isDeveloper) View.VISIBLE else View.GONE

            Glide.with(itemView)
                .load(user.avatarLink)
                .placeholder(R.drawable.ic_portrait)
                .into(avatarImageView)
        }
    }
}