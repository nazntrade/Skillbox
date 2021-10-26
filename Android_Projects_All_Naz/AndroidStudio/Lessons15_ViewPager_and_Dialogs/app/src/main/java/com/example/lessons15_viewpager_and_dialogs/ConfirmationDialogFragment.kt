package com.example.lessons15_viewpager_and_dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

//2.2   класс фрагмент для диалога, чтоб он не пропадал при перевароте экрана
                      // стандартный класс для наследования DialogFragment
class ConfirmationDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()) //this поменять на requireContext() из фрагм.
            .setTitle("Delete item")
            .setMessage("Are you sure?")
            .setPositiveButton("Yas", { _, _ ->  })
            .setNegativeButton("No", { _, _ ->  })
            .setNeutralButton("Neutral", { _, _ ->  })
            .create()
            //.show() отображать диалог тут не нужно. нужно только создать криэйт и вернуть
    }



}