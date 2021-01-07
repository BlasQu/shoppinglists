package com.example.shoppinglists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.Room.ShoppingListsDatabase
import com.example.shoppinglists.ViewModel.Viewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewmodel: Viewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewmodel.insertList(ListItem(0, "h"))
    }
}