package com.example.prosjekt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class FavoritesAdapter(private val favorites:ArrayList<FishingPlace>, private val activity: MainActivity):
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    private lateinit var card: LocationFragment
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        //UI components
        val image: View = view.findViewById(R.id.favorite_image)
        var placeName: TextView= view.findViewById(R.id.favorite_name)
        val layout:RelativeLayout = view.findViewById(R.id.item)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_card, parent, false)
        println("TESTING ON CREATE")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //updating UI components
        holder.placeName.text = favorites[position].name
        card = LocationFragment("home")

        holder.layout.setOnClickListener {
            println("HEREEEEE")
            for (place: FishingPlace in activity.getPlaces()){
                if (place.name==favorites[position].name){
                    println("INSIDE IF")
                    //Start location fragment her
                    //var args = Bundle()
                    //args.putParcelable("place", place)
                    //card.arguments = args
                    //(activity as MainActivity).setCurrentFragment(card)
                    (activity as MainActivity).getWater(place)
                    (activity as MainActivity).getSunrise(place)
                    (activity as MainActivity).getCurrentWeather(place)

                }
            }
        }


    }

    override fun getItemCount(): Int {
        return favorites.size
    }

}