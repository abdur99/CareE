package com.example.care_e

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*


class DataVisualization : Fragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var inf = inflater.inflate(R.layout.fragment_data_visualization, container, false)

        // Log.e("DATAAAAA", mParam1!!.toString())

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

        val subtitle = inf.findViewById<View>(R.id.subtext_car) as TextView

        val daySavingsView = inf.findViewById<TextView>(R.id.day_savings)
        val yearSavingsView = inf.findViewById<TextView>(R.id.year_savings)
        val monthSavingsView = inf.findViewById<TextView>(R.id.month_savings)

        val mileage = inf.findViewById<TextView>(R.id.mileage)

        val distanceBar = inf.findViewById<SeekBar>(R.id.distance_slider)

        distanceBar.max = 100
        distanceBar.min = 1
        distanceBar.progress = 65

        distanceBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

                val gasPrice = 2.8 * i / mpg!!
                val electricPrice = 1.15 * i / empg!!

                val daySavings = String.format(Locale.getDefault(), "%.2f", gasPrice - electricPrice)
                val yearSavings = String.format(Locale.getDefault(), "%.2f", (gasPrice - electricPrice) * 365)
                val monthSavings = String.format(Locale.getDefault(), "%.2f", (gasPrice - electricPrice) * 365 / 12)

                daySavingsView.setText(daySavings)
                yearSavingsView.setText(yearSavings)
                monthSavingsView.setText(monthSavings)
                mileage.setText(String.format(Locale.getDefault(), "%d", i))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        val carDescripton = "if you drove the " + emodel + " instead of the " + model
        subtitle.text = carDescripton

        return inf
    }

}

