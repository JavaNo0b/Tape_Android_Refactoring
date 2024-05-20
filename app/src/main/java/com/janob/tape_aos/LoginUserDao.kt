package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LoginUserDao {

    @Insert
    fun insert(user: LoginUser)

    @Update
    fun updateUser(user: LoginUser)

    @Query("SELECT * FROM LoginUser")
    fun getLoginUsers(): List<LoginUser>

    @Query("SELECT * FROM LoginUser WHERE nickname = :nickname")
    fun getLoginUserNickname(nickname: String): LoginUser?

    @Query("SELECT * FROM LoginUser WHERE userID = :userID")
    fun getLoginUser(userID: Long?): LoginUser?

}

