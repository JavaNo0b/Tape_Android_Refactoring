package com.janob.tape_aos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ReplyDao{
    @Insert
    fun insert(reply: Reply) : Long
    @Update
    fun update(reply: Reply)
    @Delete
    fun delete(reply: Reply)
    @Query("DELETE FROM ReplyTable")
    fun deleteAll()
    @Query("SELECT * FROM ReplyTable")
    fun getAll() :MutableList<Reply>
    @Query("UPDATE ReplyTable SET text =:text WHERE id =:id")
    fun updateReply(text: String?, id: Long?)

}