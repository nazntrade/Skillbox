package com.skillbox.lessons18_PermissionsAndDate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message.*
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class MessageAdapter : ListAdapter<Message, MessageAdapter.Holder>(MessageDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_message))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class MessageDiffUtilCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy")
            .withZone(ZoneId.systemDefault())

        fun bind(message: Message) {
            messageText.text = message.text
            createdAtText.text = formatter.format(message.createdAt)
        }
    }
}