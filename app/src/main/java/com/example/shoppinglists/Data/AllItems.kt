package com.example.shoppinglists.Data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "allItems")
data class AllItems (
        @Embedded val listItem: ListItem,
        @Relation(
                parentColumn = "itemId",
                entityColumn = "detailsId"
        )
        val details: ListDetails
) {
}