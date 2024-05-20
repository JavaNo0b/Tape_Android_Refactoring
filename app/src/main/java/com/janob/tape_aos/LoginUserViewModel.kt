package com.janob.tape_aos

import androidx.lifecycle.ViewModel

class LoginUserViewModel : ViewModel() {
    var Modeltoken :String =""
    var Modelnickname: String = ""
    var ModelprofileImg: ByteArray? = null
    var Modelintro: String? = null
}