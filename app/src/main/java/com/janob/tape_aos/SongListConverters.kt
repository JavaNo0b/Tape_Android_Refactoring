package com.janob.tape_aos

import androidx.room.TypeConverter
import com.google.gson.Gson

class SongListConverters {
    @TypeConverter
    fun listToJson(value : List<Song>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value : String): List<Song>? {
        return Gson().fromJson(value, Array<Song>::class.java)?.toList()
    }
}