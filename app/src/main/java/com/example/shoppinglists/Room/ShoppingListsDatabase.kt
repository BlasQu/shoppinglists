package com.example.shoppinglists.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shoppinglists.Data.Converters
import com.example.shoppinglists.Data.ListItem

@Database(version = 1,
    entities = [ListItem::class]
)
@TypeConverters(Converters::class)
abstract class ShoppingListsDatabase : RoomDatabase() {

    abstract fun getDao() : ShoppingListsDao
}