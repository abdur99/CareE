package com.example.care_e

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*


class DataVisualization : Fragment() {

    lateinit var CarEViewModel :ViewModel

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

        CarEViewModel = activity?.run {
            ViewModelProviders.of(this).get(ViewModel::class.java)
        } ?: throw Exception("Activity Invalid")

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


        if (CarEViewModel.TripDistanceNumber.value!! != 0.0) {

            val gasPrice =
                2.8 * (CarEViewModel.TripDistanceNumber.value!! * 1.6) / CarEViewModel.GasCar.value!!.mpg
            val electricPrice =
                1.15 * (CarEViewModel.TripDistanceNumber.value!! * 1.6) / CarEViewModel.ECar.value!!.mpg

            val daySavings = String.format(Locale.getDefault(), "%.2f", gasPrice - electricPrice)
            val yearSavings =
                String.format(Locale.getDefault(), "%.2f", (gasPrice - electricPrice) * 365)
            val monthSavings =
                String.format(Locale.getDefault(), "%.2f", (gasPrice - electricPrice) * 365 / 12)

            daySavingsView.setText(daySavings)
            yearSavingsView.setText(yearSavings)
            monthSavingsView.setText(monthSavings)
            mileage.setText(String.format(Locale.getDefault(), "%d", (BigDecimal(CarEViewModel.TripDistanceNumber.value!!*1.6).setScale(3, RoundingMode.HALF_EVEN)).toString()))


            val carDescripton = "if you drove the " + CarEViewModel.ECar.value!!.model + " instead of the " + CarEViewModel.GasCar.value!!.model
            subtitle.text = carDescripton
        }
        else {
            Toast.makeText(activity, "Please make a trip first!", Toast.LENGTH_SHORT).show()
        }

        return inf
    }

}

