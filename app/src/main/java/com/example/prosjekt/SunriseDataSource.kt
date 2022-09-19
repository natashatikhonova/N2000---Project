package com.example.prosjekt

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SunriseDataSource {
    private val sunriseURL = "https://in2000-apiproxy.ifi.uio.no/weatherapi/sunrise/2.0/.json?date=2022-04-04&lat=60&lon=11&offset=%2B01%3A00"
    private val gson = Gson()


    //suspend fun fetchSunriseData((lat: Double, long: Double, date: String)): ArrayList<Time>?{
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

    fun getMoonphase(info: ArrayList<Time>?) :String {
        var moonPhase : Double? = null

        if (info != null) {
            for (elements in info) {
                moonPhase = elements.moonphase?.value?.toDoubleOrNull()
            }
        }

        if (moonPhase != null) {
            if ((moonPhase < 0) || (moonPhase > 25)) {
                return "Waxing crescent"
            } else if ((25 < moonPhase) || (moonPhase > 50)) {
                return "Waxing gibbous"
            } else if ((50 < moonPhase) || (moonPhase > 75)) {
                return "Waning gibbous"
            } else if ((75 < moonPhase) || (moonPhase > 100)) {
                return "Waning crescent"
            }
        }

        return "no data"
    }

    fun getSunrise(info: ArrayList<Time>?): String?{
        var sunrise : String? = null

        if (info != null) {
            for (elements in info) {
                sunrise = elements.sunrise?.time
            }
        }

        //"2022-04-05T05:29:41+01:00"

        val riseTime1 = sunrise?.split("T")?.toTypedArray()
        val riseTime2 = riseTime1?.get(1)?.split("+")?.toTypedArray()
        val riseTime = riseTime2?.get(0)

        return riseTime

    }

    fun getSunset(info: ArrayList<Time>?) :String? {
        var sunset : String? = null

        if (info != null) {
            for (elements in info) {
                sunset = elements.sunset?.time
            }
        }

        //"2022-04-05T19:09:18+01:00"

        val setTime1 = sunset?.split("T")?.toTypedArray()
        val setTime2 = setTime1?.get(1)?.split("+")?.toTypedArray()
        val setTime = setTime2?.get(0)

        return setTime
    }

    /*
    fun getWindDirection(info: java.util.ArrayList<Timeseries>?): String? {
        var wind: Double? = 0.0
        var windSpeed: Int? = 0

        if (info != null) {
            for (ting in info) {
                wind = ting.data?.instant?.details?.windFromDirection
                windSpeed = ting.data?.instant?.details?.windSpeed

            }
            if (wind != null) {
                if ((wind < 45.0) || (wind > 315)) {
                    return "Wind from north with $windSpeed speed"
                } else if ((45 < wind) || (wind > 135)) {
                    return "Wind from east with $windSpeed speed"
                } else if ((135 < wind) || (wind > 225)) {
                    return "Wind from south with $windSpeed speed"
                } else if ((225 < wind) || (wind > 315)) {
                    return "Wind from west with $windSpeed speed"
                }
            }
        }
        return "test"
    }*/

}