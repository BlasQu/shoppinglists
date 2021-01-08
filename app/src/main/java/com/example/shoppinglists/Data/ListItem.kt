package com.example.shoppinglists.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lists")
data class ListItem(@PrimaryKey(autoGenerate = true) val itemId: Int,
                    val title: String,
                    val date: String,
                    var details: List<ListDetails> = emptyList<ListDetails>(),
                    var stored: Boolean = false
)