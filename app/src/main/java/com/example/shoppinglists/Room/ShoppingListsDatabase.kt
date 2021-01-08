package com.example.shoppinglists.Room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglists.Data.AllItems
import com.example.shoppinglists.Data.ListItem

@Database(version = 1,
    entities = [AllItems::class]
)
abstract class ShoppingListsDatabase : RoomDatabase() {

    abstract fun getDao() : ShoppingListsDao
}