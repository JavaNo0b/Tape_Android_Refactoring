package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SongDao {
    @Insert
    fun insert(song :Song) :Long?
    @Delete
    fun delete(song : Song)
    @Update
    fun update(song : Song)
    @Query("SELECT * FROM SongTable")
    fun getAll() : LiveData<List<Song>>
    @Query("SELECT * FROM SongTable")
    fun getAllList():List<Song>
    @Query("DELETE FROM SongTable")
    fun deleteAll()



}