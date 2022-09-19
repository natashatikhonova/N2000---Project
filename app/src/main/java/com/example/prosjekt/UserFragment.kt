package com.example.prosjekt

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import android.Manifest

class UserFragment : Fragment() {

    val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        val type = view.findViewById(R.id.type) as TextInputEditText
        val vekt = view.findViewById(R.id.vekt) as TextInputEditText
        val lengde = view.findViewById(R.id.lengde) as TextInputEditText
        val dato = view.findViewById(R.id.dato) as TextInputEditText
        val save = view.findViewById(R.id.save_button) as Button
        val getImg = view.findViewById(R.id.button_picture) as Button
        val stats = view.findViewById(R.id.back_to_stats) as ImageView

        //(activity as MainActivity).hideNavBar()

        stats.setOnClickListener {
            (activity as MainActivity).setCurrentFragment((activity as MainActivity).getStats())
            type.setText("")
            vekt.setText("")
            lengde.setText("")
            dato.setText("")
        }

        getImg.setOnClickListener {
            openGalleryForImage()
        }


        //when button is clicked data from user is saved and UI is updated
        save.setOnClickListener {
            //needs input (at least date)
            if (dato.text.toString()==""){
                val text = "Oppgi dato"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(view.context, text, duration)
                toast.show()
            } else{
                    //creating a Catch Object with data from user
                (activity as MainActivity).createCatch(type.text.toString(),vekt.text.toString(), lengde.text.toString(), dato.text.toString())
                type.setText("")
                vekt.setText("")
                lengde.setText("")
                dato.setText("")
                (activity as MainActivity).setCurrentFragment((activity as MainActivity).getStats())
            }
        }
        // Inflate the layout for this fragment
        return view
    }


    //getting picture from gallery
    private fun openGalleryForImage() {
        (activity as MainActivity).checkPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE, 101
        )
    }
}