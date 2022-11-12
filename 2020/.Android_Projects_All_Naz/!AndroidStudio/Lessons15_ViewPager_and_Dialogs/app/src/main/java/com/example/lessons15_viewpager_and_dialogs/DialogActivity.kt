package com.example.lessons15_viewpager_and_dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.lessons15_viewpager_and_dialogs.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {

    //1.1 если никто не отобразил никакой диалог, то он нулл
    private var dialog: AlertDialog? = null

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

        binding.showSingleChoiceDialogButton.setOnClickListener {
            showDialogWithSingleChoice()
        }

        binding.showCustomLayoutDialogButton.setOnClickListener {
            showCustomLayoutDialog()
        }

        binding.showDialogFragment.setOnClickListener {
            //2.2
            showDialogFragment()
        }

        binding.showButtonSheetDialogFragment.setOnClickListener {
            //3.3
            showButtonSheetDialog()
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
            .setPositiveButton("Yas", { _, _ -> toast("Clicked yes") })//_, _ lesson 15.6 t/9.00
            .setNegativeButton("No", { _, _ -> toast("Clicked no") })//_, _ lesson 15.6 t/9.00
            .setNeutralButton("Neutral", { _, _ -> toast("Clicked neutral") })
            .create()
            .show()
    }

    //создадим диалог со списком. Тут требуется массив array
    private fun showDialogWithSingleChoice() {
        val mailProviders = arrayOf("yandex", "rambler", "google", "mail.ru", "yahoo")
        AlertDialog.Builder(this)
            .setTitle("Select mail provider") //луче все класть в ресы
            .setItems(mailProviders) { _, which -> toast("Selected = ${mailProviders[which]}") }//установим элемент    15.6 t12.50
            .show()// можно без .create()
    }

    //сделали диалог со своей разметкой dialog_attention.xml
    private fun showCustomLayoutDialog() {
        //1.1 если отобразил диалог
        dialog = AlertDialog.Builder(this)
            .setView(R.layout.dialog_attention)//установим View
            .setPositiveButton("Ok") { _, _ -> } //кнопка ничего не принимает и не делает.Прочитал и закрыл
            .show()
    }

    //2.2
    private fun showDialogFragment() {
        //создадим instants(пример) фрагмента
        ConfirmationDialogFragment()
            // и отобразим. если в активити то supportFragmentManager. Если будем исп.внутри другого родит. фр.то чайлдФрагментМанаджер
            .show(supportFragmentManager, "confirmationDialogTag")
    }

    //2.2 убрать диалог програмно
    private fun hideDialog(){
        supportFragmentManager.findFragmentByTag("confirmationDialogTag")
            ?.let { it as? ConfirmationDialogFragment } //если фрагмент найден,проверим,явл-ли он...
            ?.dismiss() //и скроем, если найден
    }

    //3.3    также можно программно убрать дисмиссом
    private fun showButtonSheetDialog(){
        AvatarDialogFragment()
            .show(supportFragmentManager, "buttonSheetDialogTag")
    }

    override fun onDestroy() {
        super.onDestroy()
        //1.1 если диалог все-таки отображен(не нулл).вызвать метод дисмис(он скроет диалог)
        dialog?.dismiss()
    }

}