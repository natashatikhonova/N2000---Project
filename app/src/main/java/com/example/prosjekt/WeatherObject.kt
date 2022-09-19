package com.example.prosjekt

import android.os.Parcel
import android.os.Parcelable

//parcelable weather object from Location Forecast that containts info about temperature and wind at a certain spot
class WeatherObject(temperature: Double, wind: String): Parcelable {
    var temperature = temperature
    var wind = wind

    constructor(parcel: Parcel) : this(
        TODO("temperature"),
        TODO("wind")

    ) {
        temperature = parcel.readDouble()
        wind = parcel.readString().toString()

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