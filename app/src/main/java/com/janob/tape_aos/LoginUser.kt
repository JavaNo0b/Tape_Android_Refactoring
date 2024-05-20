package com.janob.tape_aos


import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LoginUser")
data class LoginUser(

    var userID: Long?,
    var nickname: String?,   //닉네임
    var profileimg: Bitmap?,  //프로필
    var profileintro: String?,  //소개글
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)