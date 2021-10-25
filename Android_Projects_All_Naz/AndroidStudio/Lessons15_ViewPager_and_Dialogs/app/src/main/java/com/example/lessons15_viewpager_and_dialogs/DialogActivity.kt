package com.example.lessons15_viewpager_and_dialogs

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.lessons15_viewpager_and_dialogs.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {
    lateinit var binding: ActivityDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showSimpleDialogButton.setOnClickListener {
            showSimpleDialog()
        }

        binding.showButtonDialogButton.setOnClickListener {
            showDialogWithButton()
        }
    }

    // для созд.Диалогов есть спец.класс AlertDialog
    private fun showSimpleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Simple dialog") //заголовок диалога
            .setMessage(getString(R.string.dialog_message)) //сообщение диалога.засунул сам в строковый ресурс. по уму должно быть так. было так setTitle("Simple dialog")
            .create()
        dialog.show()
    }

    private fun showDialogWithButton() {
        AlertDialog.Builder(this)
            .setTitle("Delete item")
            .setMessage("Are you sure?")
            .setPositiveButton("Yas", { _, _ -> toast("Clicked yes") }) //_, _ lesson 15.6 t/9.00
            .setNegativeButton("No", { _, _ -> toast("Clicked no") }) //_, _ lesson 15.6 t/9.00
            .setNeutralButton("Neutral", { _, _ -> toast("Clicked neutral") }) //_, _ lesson 15.6 t/9.00
            .create()
            .show()
    }
}