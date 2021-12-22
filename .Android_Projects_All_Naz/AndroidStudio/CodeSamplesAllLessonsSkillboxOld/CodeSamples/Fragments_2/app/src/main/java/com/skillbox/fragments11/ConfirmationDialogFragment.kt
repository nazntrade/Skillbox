package com.skillbox.fragments11

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.skillbox.fragment10.toast

class ConfirmationDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle("Delete item")
            .setMessage("Are you sure?")
            .setPositiveButton("Ok", { _, _ ->  })
            .setNegativeButton("No", { _, _ ->  })
            .setNeutralButton("Neutral", { _, _ ->  })
            .create()
    }

}