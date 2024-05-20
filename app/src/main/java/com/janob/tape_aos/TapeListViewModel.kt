package com.janob.tape_aos
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class TapeListViewModel :ViewModel() {
    val tapeRepository = TapeRepository.get()
    fun addTape(tape:Tape):Long?{
        return tapeRepository.addTape(tape)
    }
}