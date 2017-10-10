package com.wordpress.smdaudhilbe.nikah.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Mohammed Audhil on 11/06/17.
 * Jambav, Zoho Corp
 */

class UserData(val fireBaseUID: String?, val mailId: String, val pwd: String, val wishes: String, val date: String) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<UserData> = object : Parcelable.Creator<UserData> {
            override fun createFromParcel(source: Parcel): UserData = UserData(source)
            override fun newArray(size: Int): Array<UserData?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(fireBaseUID)
        dest.writeString(mailId)
        dest.writeString(pwd)
        dest.writeString(wishes)
        dest.writeString(date)
    }
}