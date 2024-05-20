package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class IncludedSongListViewModel : ViewModel() {

     val includedSongRepository = IncludedSongRepository.get()
     val includedSongLiveData : LiveData<List<IncludedSong>> = includedSongRepository.getInAlbum()

}