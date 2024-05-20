package com.janob.tape_aos

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IntroductionViewModel : ViewModel() {
    val tapeRepository:TapeRepository = TapeRepository.get()

    var imageUri: MutableLiveData<Uri>? = MutableLiveData()
    private var introTitle :MutableLiveData<String> = MutableLiveData()
    private var introContent:MutableLiveData<String> = MutableLiveData()

    init {

        introTitle.value=""
        introContent.value=""
    }

    fun setIntroTitle(title:String){
        introTitle.value = title
    }
    fun setIntroContent(content:String){
        introContent.value = content
    }




}