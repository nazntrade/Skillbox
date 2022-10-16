package com.skillbox.permissions_date_14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skillbox.permissions_date_14.NormalPermissionFragment
import com.skillbox.permissions_date_14.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DatetimeFragment())
                .commit()
        }
    }
}
