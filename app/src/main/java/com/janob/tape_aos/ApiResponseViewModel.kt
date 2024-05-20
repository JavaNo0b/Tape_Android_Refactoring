package com.janob.tape_aos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class ApiResponseViewModel :ViewModel() {

    var responseLiveData : LiveData<List<MelonSong>> = MutableLiveData()

    private val melonApiFetchr = MelonApiFetchr()
    private val mutalbeSearchTerm = MutableLiveData<String>()

    init{
        responseLiveData = melonApiFetchr.fetchContents()
        mutalbeSearchTerm.value = ""

        responseLiveData = mutalbeSearchTerm.switchMap { mutalbeSearchTerm -> melonApiFetchr.searchContents(mutalbeSearchTerm) }




    }

    fun fetchSearchTerm(query:String){
        mutalbeSearchTerm.value = query
    }
}