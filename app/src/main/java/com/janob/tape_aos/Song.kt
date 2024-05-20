package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(val coverImg : Int,
                val title :String,
                val singer:String,
                val album : String,
                val tapeId : Int,
                val userId : Int,
                @PrimaryKey(autoGenerate = true)
                val id : Long? = null)
