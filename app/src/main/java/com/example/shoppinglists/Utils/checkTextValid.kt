package com.example.shoppinglists.Utils

object checkTextValid {
    fun add_item_valid(text: String) : Boolean {
        if (text.isEmpty() || text.length > 25) {
            return false
        }
        return true
    }
}