package ru.skillbox.flow.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow<String> {
        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sendBlocking(s?.toString().orEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        this@textChangedFlow.addTextChangedListener(textChangedListener)
        awaitClose {
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}

fun CheckBox.checkedChangesFlow(): Flow<Boolean> {
    return callbackFlow {
        val checkedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            sendBlocking(isChecked)
        }
        setOnCheckedChangeListener(checkedChangeListener)
        awaitClose {
            setOnCheckedChangeListener(null)
        }
    }
}
