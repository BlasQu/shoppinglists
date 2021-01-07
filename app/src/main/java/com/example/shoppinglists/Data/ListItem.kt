package com.example.shoppinglists.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists")
data class ListItem(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
                    val title: String,
                    val date: String
)