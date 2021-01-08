package com.example.shoppinglists.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.FilenameFilter

data class ListDetails(val detailsId: Int, val item: String, val amount: Int, var done: Boolean)