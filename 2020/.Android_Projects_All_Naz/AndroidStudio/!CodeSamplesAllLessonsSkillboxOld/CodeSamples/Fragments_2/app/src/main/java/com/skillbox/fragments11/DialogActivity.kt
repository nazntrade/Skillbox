package com.skillbox.fragments11

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.skillbox.fragment10.toast
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity(R.layout.activity_dialog) {

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSimpleDialogButton.setOnClickListener { showSimpleDialog() }
        showButtonDialogButton.setOnClickListener {
            showDialogWithButtons()
        }
        showSingleChoiceDialogButton.setOnClickListener {
            showDialogWithSingleChoice()
        }

        showCustomLayoutDialogButton.setOnClickListener { showDialogWithCustomLayout() }
        showDialogFragment.setOnClickListener { showDialogFragment() }
        showBottomsheetDialogFragment.setOnClickListener { showBottomSheetDialog() }
    }

    private fun showSimpleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("SimpleDialog")
            .setMessage("SimpleDialogMessage")
            .create()

        dialog.show()
    }

    private fun showDialogWithButtons() {
        AlertDialog.Builder(this)
            .setTitle("Delete item")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes", { _, _ -> toast("Clicked yes") })
            .setNegativeButton("No", { _, _ -> toast("Clicked no") })
            .setNeutralButton("Neutral", { _, _ -> toast("Clicked neutral") })
            .create()
            .show()
    }

    private fun showDialogWithSingleChoice() {
        val mailProviders = arrayOf("google", "yandex", "mailru", "rambler")
        AlertDialog.Builder(this)
            .setTitle("Select mail provider")
            .setItems(mailProviders) { _, which -> toast("Selected = ${mailProviders[which]}") }
            .show()
    }

    private fun showDialogWithCustomLayout() {
        dialog = AlertDialog.Builder(this)
            .setView(R.layout.dialog_attention)
            .setPositiveButton("Ok") { _, _ -> }
            .show()
    }

    private fun showDialogFragment() {
        ConfirmationDialogFragment()
            .show(supportFragmentManager, "confirmationDialogTag")
    }

    private fun hideDialog() {
        supportFragmentManager.findFragmentByTag("confirmationDialogTag")
            ?.let { it as? ConfirmationDialogFragment }
            ?.dismiss()
    }

    private fun showBottomSheetDialog() {
        AvatarDialogFragment().show(supportFragmentManager, "tag")
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
    }
    
}