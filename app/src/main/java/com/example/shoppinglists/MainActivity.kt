package com.example.shoppinglists

import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.Fragments.ArchivedListsFragment
import com.example.shoppinglists.Fragments.ShoppingListsFragment
import com.example.shoppinglists.Room.ShoppingListsDatabase
import com.example.shoppinglists.ViewModel.Repository
import com.example.shoppinglists.ViewModel.Viewmodel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_archived_lists.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewmodel: Viewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, ShoppingListsFragment(), "ShoppingListsFragment")
            commit()
        }

        setupTabLayout()
        setupToolbar()
        setupButtons()
    }

    private fun setupButtons() {
        fab_addList.setOnClickListener {
            AlertDialogs.dialog_add_item(this)
        }
    }

    private fun changeFragment() {
        val fragment = supportFragmentManager.findFragmentByTag("ShoppingListsFragment")
        if (fragment != null && fragment.isVisible) {
            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                replace(R.id.fragment_container, ArchivedListsFragment(), "ArchivedListsFragment")
                commit()
            }
            toolbar.title = "Archived lists"
        } else {
            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right)
                replace(R.id.fragment_container, ShoppingListsFragment(), "ShoppingListsFragment")
                commit()
            }
            toolbar.title = "Shopping lists"
        }
    }

    private fun setupTabLayout() {
        tablayout.apply {
            addTab(tablayout.newTab().setIcon(R.drawable.ic_baseline_list_24).setText("Shopping lists"))
            addTab(tablayout.newTab().setIcon(R.drawable.ic_baseline_archive_24).setText("Archived lists"))
        }
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeFragment()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Shopping Lists"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.white))
        }
    }
}