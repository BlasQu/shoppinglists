package com.example.shoppinglists.Data

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJSON(toConvert: List<ListDetails>) : String {
        return Gson().toJson(toConvert)
    }

    @TypeConverter
    fun JSONtoList(toConvert: String) : List<ListDetails> {
        return Gson().fromJson(toConvert, Array<ListDetails>::class.java).toList()
    }
}