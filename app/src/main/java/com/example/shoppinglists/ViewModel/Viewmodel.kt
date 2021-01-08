package com.example.shoppinglists.ViewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.Room.ShoppingListsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Viewmodel @ViewModelInject constructor(
    val repository: Repository
) : ViewModel() {

    var data : LiveData<List<ListItem>> = repository.readLists()

    fun insertList(listItem: ListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertList(listItem)
        }
    }

    fun deleteList(listItem: ListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteList(listItem)
        }
    }

}