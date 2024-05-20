package com.janob.tape_aos

import android.content.Context
import java.util.concurrent.Executors

class TapeRepository private constructor(context:Context){
    val database = TapeDatabase.Instance(context)
    private val tapeDao = database.tapeDao()
    private val executor = Executors.newSingleThreadExecutor()
    fun addTape(tape : Tape):Long?{
        return tapeDao.insert(tape)
    }
    fun getTapeById(id:Int) = tapeDao.getTape(id)

    fun getAll() = tapeDao.getAll()

    companion object{
        var instance: TapeRepository? = null
        fun initialize(context: Context){
            if(instance == null)
                instance = TapeRepository(context)
        }
        fun get() : TapeRepository {
            return instance?:throw Exception("TapeRepository not initialized")
        }
    }
}