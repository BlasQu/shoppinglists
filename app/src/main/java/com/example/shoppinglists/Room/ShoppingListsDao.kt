package com.example.shoppinglists.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppinglists.Data.ListItem

@Dao
interface ShoppingListsDao {

    @Insert
    suspend fun insertList(list: ListItem)

    @Query("SELECT * FROM shopping_lists_table")
    fun readLists() : LiveData<List<ListItem>>
}