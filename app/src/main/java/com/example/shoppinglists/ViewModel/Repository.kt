package com.example.shoppinglists.ViewModel

import androidx.lifecycle.LiveData
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.Room.ShoppingListsDao
import javax.inject.Inject
import javax.inject.Singleton

class Repository @Inject constructor(
    private val dao: ShoppingListsDao
) {

    suspend fun insertList(list: ListItem) = dao.insertList(list)

    fun readLists() : LiveData<List<ListItem>> = dao.readLists()

    suspend fun deleteList(listItem: ListItem) = dao.deleteList(listItem)

    suspend fun updateList(listItem: ListItem) = dao.updateList(listItem)
}