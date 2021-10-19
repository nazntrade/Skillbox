package com.skillbox.permissions_date_14

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_messages.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import kotlin.random.Random

class DatetimeFragment: Fragment(R.layout.fragment_messages) {

    private var messages: List<Message> = listOf()

    private var messageAdapter: MessageAdapter? = null

    private var selectedMessageInstant: Instant? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        initMessageField()
        initTimePicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        messageAdapter = null
    }

    private fun initList() = with(messageList) {
        messageAdapter = MessageAdapter()
        adapter = messageAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        messageAdapter?.submitList(messages)
    }

    private fun initMessageField() {
        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString().takeIf { it.isNotBlank() }
                ?: return@setOnClickListener
            val newMessage = Message(
                id = Random.nextLong(),
                text = messageText,
                createdAt = selectedMessageInstant ?: Instant.now()
            )
            messages = messages + listOf(newMessage)
            messageAdapter?.submitList(messages)
            messageEditText.setText("")
            selectedMessageInstant = null
        }
    }

    private fun initTimePicker() {

        val currentDateTime = LocalDateTime.now()

        timeButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            val zonedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, hourOfDay, minute)
                                .atZone(ZoneId.systemDefault())

                            toast("Выбрано время: $zonedDateTime")
                            selectedMessageInstant = zonedDateTime.toInstant()
                        },
                        currentDateTime.hour,
                        currentDateTime.minute,
                        true
                    )
                        .show()
                },
                currentDateTime.year,
                currentDateTime.month.value - 1,
                currentDateTime.dayOfMonth
            )
                .show()
        }
    }

}