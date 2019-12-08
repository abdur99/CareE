package com.example.care_e

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.util.*

class HomeScreen : Fragment() {

    var make : String? = ""
    var model : String? = ""
    var year : Int? = 0
    var mpg : Int? = 0
    var price: Int? = 0

    var emake : String? = ""
    var emodel : String? = ""
    var eyear : Int? = 0
    var empg : Int? = 0

    var firstname : String? = ""
    var lastname : String? = ""
    var email : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        if ((arguments?.getString("Make")) != null) { make = arguments?.getString("Make").toString() }
        if ((arguments?.getString("Model")) != null) { model = arguments?.getString("Model").toString() }
        if ((arguments?.getInt("Year")) != null) { year = arguments?.getInt("Year") }
        if ((arguments?.getInt("mpg")) != null) { mpg = arguments?.getInt("mpg") }

        if ((arguments?.getString("eMake")) != null) { emake = arguments?.getString("eMake").toString() }
        if ((arguments?.getString("eModel")) != null) { emodel = arguments?.getString("eModel").toString() }
        if ((arguments?.getInt("eYear")) != null) { eyear = arguments?.getInt("eYear") }
        if ((arguments?.getInt("empg")) != null) { empg = arguments?.getInt("empg") }
        if ((arguments?.getInt("price")) != null) { price = arguments?.getInt("price") }

        if ((arguments?.getString("FirstName")) != null) { firstname = arguments?.getString("FirstName").toString() }
        if ((arguments?.getString("LastName")) != null) { lastname = arguments?.getString("LastName").toString() }
        if ((arguments?.getString("Email")) != null) { email = arguments?.getString("Email").toString() }

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

        val allinfobundle = Bundle()
        allinfobundle.putString("eMake", emake)
        allinfobundle.putString("eModel", emodel)
        allinfobundle.putInt("eYear", eyear!!.toInt())
        allinfobundle.putInt("empg", empg!!.toInt())
        allinfobundle.putInt("price", price!!.toInt())
        allinfobundle.putString("Make", make)
        allinfobundle.putString("Model", model)
        allinfobundle.putInt("Year", year!!.toInt())
        allinfobundle.putInt("mpg", mpg!!.toInt())
        allinfobundle.putString("FirstName", firstname)
        allinfobundle.putString("LastName", lastname)
        allinfobundle.putString("Email", email)

        homeview.findViewById<ImageButton>(R.id.tripinfobutton).setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_trip_record)
        }

        homeview.findViewById<ImageButton>(R.id.previoustripsbutton).setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_previous_trips)
        }

        homeview.findViewById<ImageButton>(R.id.carinfobutton).setOnClickListener {
            findNavController().navigate(R.id.action_global_navigation_car_info)
        }

//        homeview.findViewById<ImageButton>(R.id.appinfobutton).setOnClickListener {
//            findNavController().navigate(R.id.action_global_information)
//        }

        //set the string being displayed by the TextView to the greeting
        //message for the friend
        GreetingMessage.setText("$greeting!")

        return homeview
    }

}
