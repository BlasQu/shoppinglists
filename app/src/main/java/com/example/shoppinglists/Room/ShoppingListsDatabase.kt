package com.example.shoppinglists.Room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglists.Data.ListItem

@Database(version = 1,
    entities = [ListItem::class]
)
abstract class ShoppingListsDatabase : RoomDatabase() {

    abstract fun getDao() : ShoppingListsDao

    companion object {
        var instance : ShoppingListsDatabase? = null
        fun createDB(app: Context) : ShoppingListsDatabase {
            if (instance != null) {
                return instance as ShoppingListsDatabase
            }
            synchronized(this) {
                instance = Room.databaseBuilder(app, ShoppingListsDatabase::class.java, "database").build()
                return instance as ShoppingListsDatabase
            }
        }
    }
}