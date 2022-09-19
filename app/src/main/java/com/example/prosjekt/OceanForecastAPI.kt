package com.example.prosjekt

import com.google.gson.annotations.SerializedName


data class OceanForecastAPI (

    @SerializedName("type"       ) var type       : String?     = null,
    @SerializedName("geometry"   ) var geometry   : GeometryOF?   = GeometryOF(),
    @SerializedName("properties" ) var properties : PropertiesOF? = PropertiesOF()

)

data class GeometryOF (

    @SerializedName("type"        ) var type        : String?           = null,
    @SerializedName("coordinates" ) var coordinates : ArrayList<Double> = arrayListOf()

)

data class UnitsOF (

    @SerializedName("sea_surface_wave_from_direction" ) var seaSurfaceWaveFromDirection : String? = null,
    @SerializedName("sea_surface_wave_height"         ) var seaSurfaceWaveHeight        : String? = null,
    @SerializedName("sea_water_speed"                 ) var seaWaterSpeed               : String? = null,
    @SerializedName("sea_water_temperature"           ) var seaWaterTemperature         : String? = null,
    @SerializedName("sea_water_to_direction"          ) var seaWaterToDirection         : String? = null

)

data class MetaOF (

    @SerializedName("updated_at" ) var updatedAt : String? = null,
    @SerializedName("units"      ) var units     : UnitsOF?  = UnitsOF()

)

data class DetailsOF (

    @SerializedName("sea_surface_wave_from_direction" ) var seaSurfaceWaveFromDirection : Double? = null,
    @SerializedName("sea_surface_wave_height"         ) var seaSurfaceWaveHeight        : Double? = null,
    @SerializedName("sea_water_speed"                 ) var seaWaterSpeed               : Double? = null,
    @SerializedName("sea_water_temperature"           ) var seaWaterTemperature         : Double? = null,
    @SerializedName("sea_water_to_direction"          ) var seaWaterToDirection         : Double? = null

)

data class InstantOF (

    @SerializedName("details" ) var details : DetailsOF? = DetailsOF()

)

data class DataOF (

    @SerializedName("instant" ) var instant : InstantOF? = InstantOF()

)

data class TimeseriesOF (

    @SerializedName("time" ) var time : String? = null,
    @SerializedName("data" ) var data : DataOF?   = DataOF()

)

data class PropertiesOF (

    @SerializedName("meta"       ) var meta       : MetaOF?                 = MetaOF(),
    @SerializedName("timeseries" ) var timeseries : ArrayList<TimeseriesOF> = arrayListOf()

)