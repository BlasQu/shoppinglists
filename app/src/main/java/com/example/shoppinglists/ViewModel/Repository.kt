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

    fun readArchiveLists() : LiveData<List<ListItem>> = dao.readArchiveLists()

    suspend fun deleteList(listItem: List<ListItem>) = dao.deleteList(listItem)

    suspend fun updateList(listItem: ListItem) = dao.updateList(listItem)

    suspend fun updateToArchive(listItem: List<Int>) = dao.updateToArchive(listItem)

    suspend fun updateToStash(listItem: List<Int>) = dao.updateToStash(listItem)
}