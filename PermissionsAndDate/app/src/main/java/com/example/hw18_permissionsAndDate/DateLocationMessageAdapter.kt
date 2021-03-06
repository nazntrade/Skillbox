package com.example.hw18_permissionsAndDate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw18_permissionsAndDate.databinding.ItemDateLocationBinding
import org.threeten.bp.format.DateTimeFormatter

class DateLocationMessageAdapter(
    private val onItemClick: (position: Int) -> Unit
) :
    ListAdapter<DateLocationMessage, DateLocationMessageAdapter.Holder>(MessageDiffUtilCallback()) {

    class MessageDiffUtilCallback : DiffUtil.ItemCallback<DateLocationMessage>() {
        override fun areItemsTheSame(
            oldItem: DateLocationMessage,
            newItem: DateLocationMessage
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DateLocationMessage,
            newItem: DateLocationMessage
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemDateLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        private val binding: ItemDateLocationBinding,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        //инициализируем нажатия на каждый элемент списка!!!
        init {
            binding.root.setOnClickListener {
                onItemClick(bindingAdapterPosition)
            }
        }

        private val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy")
            .withZone(org.threeten.bp.ZoneId.systemDefault())

        fun bind(dateLocationMessage: DateLocationMessage) {
            binding.ImageLocation.load(dateLocationMessage.image) {
                error(R.drawable.ic_404)
            }
            binding.locationTextView.text = dateLocationMessage.location
            binding.dataTextView.text = formatter.format(dateLocationMessage.createdAt)
        }
    }
}