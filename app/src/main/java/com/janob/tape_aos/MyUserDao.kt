package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyUserDao {
    @Insert
    fun insert(user : MyUser) : Long
    @Update
    fun update(user : MyUser)
    @Delete
    fun delete(user : MyUser)
    @Query("SELECT * FROM MyUserTable")
    fun getAll() : MyUser
}