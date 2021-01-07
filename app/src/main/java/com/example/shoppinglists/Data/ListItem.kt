package com.example.shoppinglists.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_lists_table")
data class ListItem(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
                    val string: String
)