package com.example.care_e

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import java.util.*

class HomeScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var homeview = inflater.inflate(R.layout.fragment_home_screen, container, false)

        var GreetingMessage = homeview.findViewById(R.id.GreetingMessage) as TextView

        //Get the time of day
        val date = Date()
        val cal = Calendar.getInstance()
        cal.time = date
        val hour = cal.get(Calendar.HOUR_OF_DAY)

        //Set greeting
        var greeting: String? = null
        if (hour >= 12 && hour < 17) {
            greeting = "Good Afternoon"
        } else if (hour >= 17 && hour < 21) {
            greeting = "Good Evening"
        } else if (hour >= 21 && hour < 24) {
            greeting = "Good Night"
        } else {
            greeting = "Good Morning"
        }

        homeview.findViewById<ImageButton>(R.id.tripinfobutton).setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_trip_record)
        }

        homeview.findViewById<ImageButton>(R.id.previoustripsbutton).setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_previous_trips)
        }

        homeview.findViewById<ImageButton>(R.id.carinfobutton).setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_car_info)
        }

        homeview.findViewById<ImageButton>(R.id.appinfobutton).setOnClickListener {
            findNavController().navigate(R.id.action_global_information)
        }

        //set the string being displayed by the TextView to the greeting
        //message for the friend
        GreetingMessage.setText("$greeting!")

        return homeview
    }

}
