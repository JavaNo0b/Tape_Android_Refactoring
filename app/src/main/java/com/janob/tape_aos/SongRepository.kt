package com.janob.tape_aos

import android.content.Context
import androidx.lifecycle.LiveData
import java.lang.IllegalStateException

private const val DATABASE_NAME = "song-database"
class SongRepository private constructor(val context: Context){

    //객체 참조
    private val database : TapeDatabase = TapeDatabase.Instance(context)
    private val songDao = database.songDao()

    fun getAll() : LiveData<List<Song>> = songDao.getAll()
    //fun get(id :Int) = songDao.get(id)

    companion object{
        private var INSTANCE : SongRepository? = null
        fun initialize(context:Context){
            if(INSTANCE == null)
                INSTANCE = SongRepository(context)

        }
        fun get() : SongRepository{
            return INSTANCE ?: throw IllegalStateException("Song Repository must be initialized")
        }


    }


}