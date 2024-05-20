package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IncludedSongTable")
data class IncludedSong(
    var albumTitle :String="",
    var title : String = "",
    var singer  : String = "",
    var img: Int? = 0,
    var tapeIdx: Int? = 0,
    var isLiked: Boolean = false

){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}