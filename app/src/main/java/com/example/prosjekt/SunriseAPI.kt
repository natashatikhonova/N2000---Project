package com.example.prosjekt

data class Astrodata(val noNamespaceSchemaLocation : String?, val meta: Meta2?, val xmlns : String?, val location: Location?)

data class Base(val astrodata: Astrodata?)

data class High_moon(val elevation: String?, val time: String?, val desc: String?)

data class Location(val latitude: String?, val time: ArrayList<Time>?, val height: String?, val longitude: String?)

data class Low_moon(val elevation: String?, val time: String?, val desc: String?)

data class Meta2(val licenseurl: String?)

data class Moonphase(val time: String?, val value: String?, val desc: String?)

data class Moonposition(val elevation: String?, val phase: String?, val range: String?,
                        val azimuth: String?, val time: String?, val desc: String?)

data class Moonrise(val time: String?, val desc: String?)

data class Moonset(val time: String?, val desc: String?)

data class Moonshadow(val elevation: String?, val azimuth: String?, val time: String?, val desc: String?)

data class Solarmidnight(val elevation: String?, val time: String?, val desc: String?)

data class Solarnoon(val elevation: String?, val time: String?, val desc: String?)

data class Sunrise(val time: String?, val desc: String?)

data class Sunset(val time: String?, val desc: String?)

data class Time(val date: String?, val solarnoon: Solarnoon?, val moonset: Moonset?,
                val sunrise: Sunrise?, val moonphase: Moonphase?, val moonshadow: Moonshadow?,
                val moonposition: Moonposition?, val sunset: Sunset?, val moonrise: Moonrise?,
                val solarmidnight: Solarmidnight?, val low_moon: Low_moon?, val high_moon: High_moon?)
