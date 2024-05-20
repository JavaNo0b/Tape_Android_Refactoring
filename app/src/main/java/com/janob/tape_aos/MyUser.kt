package com.janob.tape_aos

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyUserTable")
data class MyUser(
    var userImg : Bitmap? = null,
    var name : String = "",
    var comment : String = "",
    // 팔로워, 팔로잉 목록
    var followerList : List<String>,
    var followingList : List<String>,
    // 테이프 목록
    var tapeList : List <Tape>,
    var likeList : List<Song>,
    @PrimaryKey(autoGenerate = true) var id : Long? = null
)
