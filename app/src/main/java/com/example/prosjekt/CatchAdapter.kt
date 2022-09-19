package com.example.prosjekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CatchAdapter(private val cathes:ArrayList<Catch>, private val activity: MainActivity):
    RecyclerView.Adapter<CatchAdapter.ViewHolder>() {
    private lateinit var card: CatchFragment
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        //UI components
        val image: ImageView = view.findViewById(R.id.catch_image)
        var date: TextView= view.findViewById(R.id.catch_date)
        val layout:RelativeLayout = view.findViewById(R.id.item_catch)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catch_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //updating UI components
        holder.date.text = cathes[position].date
        Glide.with(holder.itemView).load(cathes[position].image).into(holder.image)
        card = CatchFragment()

        holder.layout.setOnClickListener {
            for (catch: Catch in activity.getCatches()){
                    if (catch.date==cathes[position].date){
                        var args = Bundle()
                        args.putParcelable("catch", catch)
                        card.arguments = args
                        activity.setCurrentFragment(card)
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return cathes.size
    }

}