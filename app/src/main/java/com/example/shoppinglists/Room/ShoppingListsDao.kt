package com.example.shoppinglists.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglists.Data.ListItem

@Dao
interface ShoppingListsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(listItem : ListItem = ListItem(0, "Hello", "d"))

    @Query("SELECT * FROM lists")
    fun readLists() : LiveData<List<ListItem>>

    @Delete
    suspend fun deleteList(listItem: ListItem)
}