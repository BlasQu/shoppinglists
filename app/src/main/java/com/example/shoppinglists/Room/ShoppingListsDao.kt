package com.example.shoppinglists.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglists.Data.ListItem

@Dao
interface ShoppingListsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(listItem : ListItem)

    @Query("SELECT * FROM lists order by itemId DESC")
    fun readLists() : LiveData<List<ListItem>>

    @Update
    suspend fun updateList(listItem: ListItem)

    @Delete
    suspend fun deleteList(listItem: ListItem)
}