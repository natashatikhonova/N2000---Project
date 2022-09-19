package com.example.prosjekt

import com.google.gson.annotations.SerializedName

//data classes that are needed for implementing fishing API

//object for parsing from JSON  - contains all the info from API
data class FishingAPI (

    @SerializedName("displayFieldName" ) var displayFieldName : String?             = null,
    @SerializedName("fieldAliases"     ) var fieldAliases     : FieldAliases?       = FieldAliases(),
    @SerializedName("geometryType"     ) var geometryType     : String?             = null,
    @SerializedName("spatialReference" ) var spatialReference : SpatialReference?   = SpatialReference(),
    @SerializedName("fields"           ) var fields           : ArrayList<Fields>   = arrayListOf(),
    @SerializedName("features"         ) var features         : ArrayList<Features> = arrayListOf()

)

//not used in the project - needed for parsing
data class FieldAliases(
    @SerializedName("stedsnavn" ) var stedsnavn : String? = null,
    @SerializedName("komm"      ) var komm      : String? = null
)

//not used in the project - needed for parsing
data class SpatialReference(
    @SerializedName("wkid"       ) var wkid       : Int? = null,
    @SerializedName("latestWkid" ) var latestWkid : Int? = null
)

//not used in the project - needed for parsing
data class Fields(
    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("type"   ) var type   : String? = null,
    @SerializedName("alias"  ) var alias  : String? = null,
    @SerializedName("length" ) var length : Int?    = null
)

//used in the project - fishing place object that contains info about name and location
data class Features(
    @SerializedName("attributes" ) var attributes : Attributes? = Attributes(),
    @SerializedName("geometry"   ) var geometry   : Geometry?   = Geometry()
)

//used in the project - object that contains info about name and area
data class Attributes(
    @SerializedName("stedsnavn" ) var stedsnavn : String? = null,
    @SerializedName("komm"      ) var komm      : String? = null
)

//used in the project - object that contains a list with coordinates
data class Geometry(
    @SerializedName("rings" ) var rings : ArrayList<ArrayList<ArrayList<Double>>> = arrayListOf()
)

