package com.example.prosjekt

import android.os.Parcel
import android.os.Parcelable

class FishingPlace(placeName: String, lat: Double?, long: Double) : Parcelable {
    var name: String = placeName
    var north: Double = lat!!
    var east: Double = long!!
    var favorite:Boolean = false
    var temperature:Double? = 0.0
    var windDir:String? = null
    var moon:String? = null
    var soloppgang: String? = null
    var solnedgang: String? = null
    var water: Double? = null

    constructor(parcel: Parcel) : this(
        TODO("placeName"),
        TODO("lat"),
        TODO("long")
    ) {
        name = parcel.readString().toString()
        north = parcel.readDouble()
        east = parcel.readDouble()
        temperature = parcel.readDouble()
        windDir = parcel.readString().toString()
        moon = parcel.readString().toString()
        soloppgang = parcel.readString().toString()
        solnedgang = parcel.readString().toString()
        water = parcel.readDouble()
        favorite = parcel.readByte() != 0.toByte()
    }

    fun setWater(temp: Double){
        water = temp
    }

    fun setTemperature(temp: Double){
        temperature = temp
    }

    fun setWind(windInfo: String){
        windDir = windInfo
    }

    fun setMoonPhase(moonphase: String){
        moon = moonphase
        println("SETTING MOONPHASE: "+moon)
    }

    fun setSunrise(sun: String){
        soloppgang = sun
    }

    fun setSunset(sun: String){
        solnedgang = sun
    }

    fun getNorth(): Double?{
        return north
    }

    fun getEast(): Double?{
        return east
    }

    fun makeFavorite(){
        favorite = true
    }

    fun unmakeFavorite(){
        favorite = false
    }

    fun isFavorite():Boolean {
        return favorite
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<FishingPlace> {
        override fun createFromParcel(parcel: Parcel): FishingPlace {
            return FishingPlace(parcel)
        }

        override fun newArray(size: Int): Array<FishingPlace?> {
            return arrayOfNulls(size)
        }
    }
}