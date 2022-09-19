package com.example.prosjekt

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class LocationFragment(private val fragment: String) : Fragment(){

    private lateinit var fishingPlace: FishingPlace
    //private lateinit var sunriseObj: SunriseObject
    private lateinit var weatherObj: WeatherObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)

        val name: TextView = view.findViewById(R.id.location_name)
        val weather:TextView = view.findViewById(R.id.location_weather)
        val moon:TextView = view.findViewById(R.id.location_moon)
        val sun:TextView = view.findViewById(R.id.location_sun)
        val water:TextView = view.findViewById(R.id.location_water)
        val heart: ImageView = view.findViewById(R.id.heart_icon)
        val arrow: ImageView = view.findViewById(R.id.arrow_icon)

        val bundle = this.arguments
        if (bundle != null) {
            fishingPlace = bundle.getParcelable("place")!! // Key
            weatherObj = bundle.getParcelable("weather")!!
            //sunriseObj = bundle.getParcelable("sunrise")!!
            name.text = fishingPlace.name

            weather.text = "Temperatur: "+weatherObj.temperature.toString() +"°\n\n"+weatherObj.wind

            moon.text = "Månefase: "+ weatherObj.moon

            sun.text = "Soloppgang: "+weatherObj.sunrise+"\n\nSolnegang: "+weatherObj.sunset

            /*
            if (weatherObj.moon !=null){
                moon.text = "Månefase: "+ weatherObj.moon
            }else{
                moon.text = "Månefase: data er dessverre ikke tilgjengelig"
            }
            if(weatherObj.sunrise==null || weatherObj.sunset==null){
                sun.text = "Soloppgang/solnedgang: data er dessverre ikke tilgjengelig"
            }else{
                sun.text ="HEIHEI"
                sun.text = "Soloppgang: kl."+weatherObj.sunrise
                // sun.text = "Soloppgang: kl."+sunriseObj.sunrise+"\n\nSolnegang: kl."+sunriseObj.sunset
            }

            if (weatherObj != null){
                weather.text = "Temperatur: "+weatherObj.temperature.toString() +"°\n\n"+weatherObj.wind
            }else{
                weather.text = "Temperatur: data er dessverre ikke tilgjengelig"
            }*/

            /*if(sunriseObj.water!=null){
                water.text = "Vanntemperatur: "+sunriseObj.water+"°"
            }else{
                water.text = "Vanntemperatur: data er dessverre ikke tilgjengelig"
            }*/


            //moon.text = "Månefase: "+ fishingPlace.moon
            //sun.text = "Soloppgang: "+fishingPlace.soloppgang+"\n\nSolnegang: "+fishingPlace.solnedgang
        }

        if(fishingPlace.isFavorite()){
            heart.setImageResource(R.drawable.red_heart_foreground);
        }

        heart.setOnClickListener {
            if(fishingPlace.isFavorite()){
                (activity as MainActivity).removeFromFavorites(fishingPlace)
                println("FAVORITE REMOVED")
                heart.setImageResource(R.drawable.heart_foreground);
            } else{
                (activity as MainActivity).addToFavorites(fishingPlace)
                println("NEW FAVORITE ADDED")
                heart.setImageResource(R.drawable.red_heart_foreground);
            }
        }

        arrow.setOnClickListener {
            if (fragment == "home") {
                (activity as MainActivity).setCurrentFragment((activity as MainActivity).getHome())
            } else if (fragment == "maps") {
                (activity as MainActivity).setCurrentFragment((activity as MainActivity).getMaps())
            }

        }

        return view
    }
}