package com.example.shoppinglists.Room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppinglists.Data.ListItem

@Database(version = 1,
    entities = [ListItem::class]
)
abstract class ShoppingListsDatabase : RoomDatabase() {

    abstract fun getDao() : ShoppingListsDao
}