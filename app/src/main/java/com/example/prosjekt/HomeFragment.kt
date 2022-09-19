package com.example.prosjekt

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomeFragment : Fragment() {
    private lateinit var card: LocationFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val textView = view.findViewById(R.id.autocomplete_search_bar) as AutoCompleteTextView
        //recyclerView
        val recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        val favoritesAdapter = FavoritesAdapter((activity as MainActivity).getFavorites(),activity as MainActivity )
        recyclerView.adapter = favoritesAdapter
        // Get the string array
        val fishingPlaces: ArrayList<String> = (activity as MainActivity).getNames()
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String>(activity as MainActivity, android.R.layout.simple_list_item_1, fishingPlaces).also { adapter ->
            textView.setAdapter(adapter)
        }
        card = LocationFragment("home")

        textView.setOnItemClickListener(OnItemClickListener { parent, view, position, rowId ->
            val selection = parent.getItemAtPosition(position) as String
            textView.text.clear()
            hideKeyboard()
            for (place: FishingPlace in (activity as MainActivity).getPlaces()){
                if (place.name==selection){

                    //Start location fragment her
                    //(activity as MainActivity).getWater(place)
                    //(activity as MainActivity).getSunrise(place)
                    (activity as MainActivity).getCurrentWeather(place)
                }
            }
        })

        // Inflate the layout for this fragment
        return view
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }





}


