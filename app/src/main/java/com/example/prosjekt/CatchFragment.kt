package com.example.prosjekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class CatchFragment : Fragment() {

    private lateinit var catch:Catch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catch, container, false)
        val arrow = view.findViewById(R.id.arrow_back) as ImageView
        val date = view.findViewById(R.id.date) as TextView
        val image = view.findViewById(R.id.circle_img) as CircleImageView
        val type = view.findViewById(R.id.type) as TextView
        val weight = view.findViewById(R.id.weight) as TextView
        val length= view.findViewById(R.id.length) as TextView
        val delete = view.findViewById(R.id.delete_button) as Button

        val bundle = this.arguments
        if (bundle != null) {
            catch = bundle.getParcelable("catch")!! // Key
            date.text = catch.date
            Glide.with(view).load(catch.image).into(image)
            type.text = catch.type
            weight.text = catch.weight
            length.text = catch.length
        }
        arrow.setOnClickListener {
            (activity as MainActivity).setCurrentFragment((activity as MainActivity).getStats())
        }

        delete.setOnClickListener {
            (activity as MainActivity).removeCatch(catch)
            (activity as MainActivity).setCurrentFragment((activity as MainActivity).getStats())
        }
        return view
    }
}