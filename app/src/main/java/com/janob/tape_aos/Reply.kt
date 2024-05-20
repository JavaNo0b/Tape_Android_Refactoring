package com.janob.tape_aos

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="ReplyTable")
data class Reply(var idx: Int,
                 var text: String?,
                 @PrimaryKey(autoGenerate = true)
                 var id:Long? = null) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idx)
        parcel.writeString(text)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reply> {
        override fun createFromParcel(parcel: Parcel): Reply {
            return Reply(parcel)
        }

        override fun newArray(size: Int): Array<Reply?> {
            return arrayOfNulls(size)
        }
    }

}