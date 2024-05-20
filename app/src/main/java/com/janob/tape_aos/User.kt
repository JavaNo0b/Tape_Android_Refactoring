package com.janob.tape_aos

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    // userKey=1 -> 사용자, userKey=0 -> 타유저
    var userKey : Int? = null,
    //var userImg : Int? = null,
    var userImg : Bitmap? = null,
    //var userImg : String? = null,
    var name : String = "",
    var comment : String = "",
        // 팔로워, 팔로잉 목록
    var followerList : List<String>,
    var followingList : List<String>,
        // 테이프 목록
    //@SerializedName("tapeList")
    var tapeList : List <Tape>,
    var likeList : List<Song>,
    @PrimaryKey(autoGenerate = true) var id : Long? = null
)
