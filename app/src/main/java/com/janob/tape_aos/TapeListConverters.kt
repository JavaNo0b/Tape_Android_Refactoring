package com.janob.tape_aos

import androidx.room.TypeConverter
import com.google.gson.Gson

class TapeListConverters {
    @TypeConverter
    fun listToJson(value : List<Tape>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value : String): List<Tape>? {
        return Gson().fromJson(value, Array<Tape>::class.java)?.toList()
    }
}