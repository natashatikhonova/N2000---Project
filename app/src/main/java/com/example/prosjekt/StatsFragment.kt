package com.example.prosjekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StatsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)
        val button:Button = view.findViewById(R.id.add_fish_button)

        val recyclerView = view.findViewById(R.id.catches) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 2,RecyclerView.VERTICAL ,false)
        val catchesAdapter = CatchAdapter((activity as MainActivity).getCatches(),activity as MainActivity )
        recyclerView.adapter = catchesAdapter

        button.setOnClickListener {
            (activity as MainActivity).setCurrentFragment((activity as MainActivity).getScheme())
        }
        // Inflate the layout for this fragment
        return view
    }


}