package com.janob.tape_aos

import androidx.room.PrimaryKey

data class Alarm(

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var reciver_id : String = "",
    var alarm_id : String = "",
    var is_checked : Boolean = false,
    var is_notification : Boolean = false
){
    companion object{
        const val notif_1 = 0
        const val notif_2 = 1
        const val  notif_3 = 2
    }
}
