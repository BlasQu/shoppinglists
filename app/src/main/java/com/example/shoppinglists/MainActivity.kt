package com.example.shoppinglists

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.shoppinglists.Fragments.ArchivedListsFragment
import com.example.shoppinglists.Fragments.ShoppingListsFragment
import com.example.shoppinglists.Utils.AlertDialogs
import com.example.shoppinglists.Utils.checkCurrentFragment
import com.example.shoppinglists.ViewModel.Viewmodel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

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

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("DetailsListsFragment") != null) onSupportNavigateUp()
        else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (fab_addList.isOrWillBeShown) {
            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right)
                replace(R.id.fragment_container, ShoppingListsFragment(), "ShoppingListsFragment")
                commit()
            }
            supportActionBar!!.title = "Shopping lists"
        } else {
            supportFragmentManager.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                replace(R.id.fragment_container, ArchivedListsFragment(), "ArchivedListsFragment")
                commit()
            }
            supportActionBar!!.title = "Archived lists"
        }
        tablayout.animation = AnimationUtils.loadAnimation(this, R.anim.alpha_out).apply {
            tablayout.visibility = View.VISIBLE
            interpolator = FastOutSlowInInterpolator()
            duration = 250
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        return super.onSupportNavigateUp()
    }

    private fun setupButtons() {
        fab_addList.setOnClickListener {
            when (checkCurrentFragment.checkFragment(this)) {
                1 -> AlertDialogs.dialog_add_item(this)
                2 -> AlertDialogs.dialog_add_details(this)
            }
        }
    }

    private fun setupTabLayout() {
        tablayout.apply {
            addTab(tablayout.newTab().setIcon(R.drawable.ic_baseline_list_24).setText("Shopping lists"))
            addTab(tablayout.newTab().setIcon(R.drawable.ic_baseline_archive_24).setText("Archived lists"))
        }
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction().apply {
                            setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right)
                            replace(R.id.fragment_container, ShoppingListsFragment(), "ShoppingListsFragment")
                            commit()
                        }
                        toolbar.title = "Shopping lists"
                        fab_addList.apply {
                            isClickable = true
                            show()
                        }
                    }
                    1 -> {
                        supportFragmentManager.beginTransaction().apply {
                            setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                            replace(R.id.fragment_container, ArchivedListsFragment(), "ArchivedListsFragment")
                            commit()
                        }
                        toolbar.title = "Archived lists"
                        fab_addList.apply {
                            isClickable = false
                            hide()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Shopping lists"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.white))
        }
    }
}