package com.example.prosjekt

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DataSource {
    //API from Fiskedirektoratet
    private val fishingAPI = "https://gis.fiskeridir.no/server/rest/services/Yggdrasil/Kystn%C3%A6re_fiskeridata/MapServer/3/query?where=1%3D1&outFields=stedsnavn,komm&outSR=4326&f=json"

    private val gson = Gson()

    //getting information about fishing places (name, kommune, coordinates)
    //using Fuel and gson to parse json into Kotlin object
    suspend fun fetchFishingPlaces(): ArrayList<Features>? {
        try{
            return gson.fromJson(Fuel.get(fishingAPI).awaitString(), FishingAPI::class.java).features
        }catch(exception:Exception){
            println("A network request exception was thrown: ${exception.message}")
            return null
        }
    }

    //getting information about weather at a certain location
    suspend fun fetchWeather(lat: Double, long: Double): List<Timesery>? {
        try{
            val first:Double = String.format("%.3f", lat).toDouble()
            val second: Double = String.format("%.3f", long).toDouble()
            return gson.fromJson(Fuel.get("https://in2000-apiproxy.ifi.uio.no/weatherapi/locationforecast/2.0/compact?lat=${first}&lon=${second}").awaitString(), LocationForecastAPI::class.java).properties?.timeseries
        }catch(exception:Exception){
            println("A network request exception was thrown: ${exception.message}")
            return null
        }
    }

    //getting information about water temperature at a certain location
    suspend fun fetchWater(lat: Double, long: Double): Double? {
        try{
            val first:Double = String.format("%.3f", lat).toDouble()
            val second: Double = String.format("%.3f", long).toDouble()
            return gson.fromJson(Fuel.get("https://in2000-apiproxy.ifi.uio.no/weatherapi/oceanforecast/2.0/complete?lat=${first}&lon=${second}").awaitString(), OceanForecastAPI::class.java).properties?.timeseries?.last()?.data?.instant?.details?.seaWaterTemperature
        }catch(exception:Exception){
            println("A network request exception was thrown: ${exception.message}")
            return null
        }
    }

    //getting information about sunrise/sunset and moonphase at a certain location
    suspend fun fetchSunriseData(lat: Double, long: Double): ArrayList<Time>?{
        try {
            val first:Double = String.format("%.3f", lat).toDouble()
            val second: Double = String.format("%.3f", long).toDouble()
            val sdf = SimpleDateFormat("yyyy-M-dd")
            val currentDate = sdf.format(Date()).split("-")?.toTypedArray()
            val day = currentDate.get(2)
            var month = currentDate.get(1)
            val year = currentDate.get(0)

            if(month.toInt()<10){
                month = "0"+month
            }

            val response = gson.fromJson(Fuel.get("https://in2000-apiproxy.ifi.uio.no/weatherapi/sunrise/2.0/.json?date=${year}-${month}-${day}&lat=${first}&lon=${second}&offset=%2B01%3A00").awaitString(), Astrodata::class.java).location?.time
            return response
        } catch (exception: Exception) {
            println("A network request exception was thrown: ${exception.message}")
            return null
        }
    }
}