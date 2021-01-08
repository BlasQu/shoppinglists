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

    var data: LiveData<List<ListItem>> = repository.readLists()
    var archivedata: LiveData<List<ListItem>> = repository.readArchiveLists()
    var currentDetails: Int = 0
    var selectedItems2 = MutableLiveData<Int>(0)
    var result = MutableLiveData<Boolean>(false)

    fun insertList(listItem: ListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertList(listItem)
        }
    }

    fun deleteList(listItem: List<ListItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteList(listItem)
        }
    }

    fun updateList(listItem: ListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateList(listItem)
        }
    }

    fun updateToArchive(listItems: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateToArchive(listItems)
        }
    }

    fun updateToStash(listItems: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateToStash(listItems)
        }
    }

}