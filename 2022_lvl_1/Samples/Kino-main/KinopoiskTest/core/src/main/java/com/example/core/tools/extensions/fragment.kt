package com.example.core.tools.extensions

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.core.R
import com.example.core.tools.all.LoadState
import kotlinx.android.synthetic.main.add_dialog.view.*
import kotlinx.android.synthetic.main.error_dialog.view.*
import kotlinx.android.synthetic.main.error_dialog.view.close_button
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun Fragment.createErrorDialog(message: String? = null) {
    val view = LayoutInflater.from(requireContext())
        .inflate(R.layout.error_dialog, null)
    val closeButton = view.close_button
    val errorMessage = view.message

    if (message != null)
        errorMessage.text = message

    val dialog = AlertDialog.Builder(requireContext())
        .setView(view)
        .create()

    closeButton.setOnClickListener { dialog.dismiss() }

    dialog.show()
}

fun Fragment.createAddDialog(addCollection: (name: String) -> Unit) {
    val view = LayoutInflater.from(requireContext())
        .inflate(R.layout.add_dialog, null)
    val positiveButtonClickListener = view.positive_button
    val negativeButton = view.close_button
    val editText = view.edit_text

    val dialog = AlertDialog.Builder(requireContext())
        .setView(view)
        .create()

    positiveButtonClickListener.setOnClickListener {
        addCollection(editText.text.toString())
        dialog.dismiss()
    }

    negativeButton.setOnClickListener { dialog.dismiss() }

    dialog.show()

}

fun Fragment.observeLoadState(
    stateFlow: StateFlow<LoadState>,
    progressBar: View,
    block: () -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        stateFlow.collect { state ->
            when (state) {
                LoadState.LOADING -> progressBar.isVisible = true
                LoadState.NOT_RESULT -> block()
                LoadState.SUCCESS -> progressBar.isVisible = false
                LoadState.ERROR -> createErrorDialog()
                LoadState.FHF -> createErrorDialog("Страница не найдена, ошибка 404")
            }
        }
    }
}