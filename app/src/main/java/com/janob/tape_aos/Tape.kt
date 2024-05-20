package com.janob.tape_aos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "TapeTable")

data class Tape(

    var tapeTitle: String = "",   // 테이프 이름
    var singer : String = "",
    var userName : String = "",
    var albumCover : Int ?= null,
      //유저 동그라미 화면
    var userImage : Int ?= null,
    var isLike: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id : Long? = null,
)

