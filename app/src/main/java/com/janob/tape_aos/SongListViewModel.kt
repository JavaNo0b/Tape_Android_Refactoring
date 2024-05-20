package com.janob.tape_aos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SongListViewModel : ViewModel() {

    val songRepository : SongRepository = SongRepository.get()
    val songListLiveData = songRepository.getAll()

    //은닉
    var songsCount : MutableLiveData<Int> = MutableLiveData()

    init{
        songsCount.value = 0
    }
    fun plusSong(){
        songsCount.value = (songsCount.value)?.plus(1)
    }
    fun minusSong(){
        songsCount.value = (songsCount.value)?.minus(1)
    }



}