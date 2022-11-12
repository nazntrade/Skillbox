package com.skillbox.constraintlayoutandtoolbars

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import kotlinx.android.synthetic.main.activity_toolbar.*

class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        toolbar.setNavigationOnClickListener {
            toast("navigation clicked")
        }

        val searchItem = toolbar.menu.findItem(R.id.action_search)
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                collapseStateTextView.text = "search expanded"
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                collapseStateTextView.text = "search collapsed"
                return true
            }
        })

        (searchItem.actionView as SearchView).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    queryStateTextView.text = "submitted = $query"
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    queryStateTextView.text = "search text = $newText"
                    return true
                }
            })

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action2 -> {
                    toast("option 2")
                    true
                }
                R.id.action3 -> {
                    toast("option 3")
                    true
                }
                else -> false
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
