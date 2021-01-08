package com.example.shoppinglists.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglists.Data.ListItem

@Dao
interface ShoppingListsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(listItem : ListItem)

    @Query("SELECT * FROM lists WHERE stored = 0 order by itemId DESC")
    fun readLists() : LiveData<List<ListItem>>

    @Query("SELECT * FROM lists WHERE stored = 1 order by itemId DESC")
    fun readArchiveLists() : LiveData<List<ListItem>>

    @Query("UPDATE lists SET stored = 1 WHERE itemId in (:listItem)")
    suspend fun updateToArchive(listItem: List<Int>)

    @Query("UPDATE lists SET stored = 0 WHERE itemId in (:listItem)")
    suspend fun updateToStash(listItem: List<Int>)

    @Update
    suspend fun updateList(listItem: ListItem)

    @Delete
    suspend fun deleteList(listItem: List<ListItem>)

    @Query("SELECT * FROM lists order by itemId DESC")
    fun testReadLists() : List<ListItem>
}