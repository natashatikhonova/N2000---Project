package com.example.prosjekt

import android.os.Parcel
import android.os.Parcelable

class Catch(image: String?, type: String?, weight: String?, length: String?, date: String?): Parcelable {
    val image = image
    var type = type
    var weight = weight
    var length = length
    var date = date
    constructor(parcel: Parcel) : this(
        TODO("type"),
        TODO("weight"),
        TODO("length"),
        TODO("date"),
        TODO("image")
    ) {
        image = parcel.readString().toString()
        type = parcel.readString().toString()
        weight= parcel.readString().toString()
        length = parcel.readString().toString()
        date= parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SunriseObject> {
        override fun createFromParcel(parcel: Parcel): SunriseObject {
            return SunriseObject(parcel)
        }

        override fun newArray(size: Int): Array<SunriseObject?> {
            return arrayOfNulls(size)
        }
    }

}
