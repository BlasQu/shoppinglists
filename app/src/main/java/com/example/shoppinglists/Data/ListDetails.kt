package com.example.shoppinglists.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.FilenameFilter

@Entity(tableName = "listDetails")
data class ListDetails(@PrimaryKey(autoGenerate = true) val detailsId: Int, val item: String, val amount: Int)