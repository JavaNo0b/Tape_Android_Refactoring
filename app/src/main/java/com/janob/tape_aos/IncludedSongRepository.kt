package com.janob.tape_aos

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class IncludedSongRepository private constructor(context : Context) {
    //객체 참조
    private val database =  TapeDatabase.Instance(context)
    private val includedSongDao = database.IncludedSongDao()
    //작업자 Thread 에서 데이터 변경과 추가
    private val executor = Executors.newSingleThreadExecutor()
    fun update(song:IncludedSong){
        executor.execute{
            includedSongDao.update(song)
        }
    }
    fun add(song:IncludedSong) :Long?{

        return includedSongDao.insert(song)

    }
    fun delete(song:IncludedSong){
        executor.execute {
            includedSongDao.delete(song)
        }

    }
    fun deleteById(id:Long?){
        executor.execute {
            includedSongDao.deleteById(id)
        }
    }
    //임시코드
    fun getInAlbum() :LiveData<List<IncludedSong>>{
        return includedSongDao.getSongsInAlbumLiveData(10)
    }
    companion object{
        private var instance : IncludedSongRepository? = null
        fun initialize(context : Context){
            if(instance == null)
                instance = IncludedSongRepository(context)
        }
        fun get() : IncludedSongRepository{
            return instance?: throw Exception("IncludedSong Repository not initialized")
        }
    }
}