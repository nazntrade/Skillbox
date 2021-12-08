package com.example.hw18_permissionsAndDate

import android.view.View
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DateLocationMessageAdapter: ListAdapter<DateLocationMessage, DateLocationMessageAdapter.Holder> {




    class Holder(
    override val containerView: View
    ): RecyclerView.ViewHolder
}