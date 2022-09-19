package com.example.prosjekt

import android.os.Parcel
import android.os.Parcelable

//parcelable weather object from Location Forecast that containts info about temperature and wind at a certain spot
class SunriseObject(sunrise: String, sunset:String, moon: String): Parcelable {
    var sunrise = sunrise

    var sunset = sunset
    var moon = moon
    //var water = water
    constructor(parcel: Parcel) : this(
        TODO("sunrise"),
        TODO("sunset"),
        TODO("moon")
    ) {

        sunrise = parcel.readString().toString()

        sunset = parcel.readString().toString()
        moon = parcel.readString().toString()
        //water = parcel.readString().toString()
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