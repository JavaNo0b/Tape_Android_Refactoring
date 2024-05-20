package com.janob.tape_aos
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface TapeDao {
    @Insert
    fun insert(tape: Tape) : Long?
    @Update
    fun update(tape : Tape)
    @Delete
    fun delete(tape: Tape)
    @Query("DELETE FROM TapeTable")
    fun deleteAll()
    @Query("SELECT * FROM TapeTable")
    fun getAll() : List<Tape>
    @Query("SELECT * FROM TapeTable WHERE id = :id")
    fun getTape(id: Int): Tape
//    @Query("UPDATE SongTable SET isLike = :isLike WHERE id = :id")
//    fun updateIsLikeById(isLike: Boolean,id: Int)
}