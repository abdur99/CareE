package com.example.care_e

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DataVisualization.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DataVisualization.newInstance] factory method to
 * create an instance of this fragment.
 */
class DataVisualization : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: Bundle? = null
    private var mParam2: Bundle? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getBundle(ARG_PARAM1)
            mParam2 = it.getBundle(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inf = inflater.inflate(R.layout.fragment_data_visualization, container, false)

        Log.e("DATAAAAA", mParam1!!.toString())

        val gasCar = Car(mParam1!!.getString("make")!!,
            mParam1!!.getString("model")!!,
            mParam1!!.getInt("year"),
            mParam1!!.getInt("mpg"))
        val eCar = ElectricCar(mParam2!!.getString("eMake")!!,
            mParam2!!.getString("eModel")!!,
            mParam2!!.getInt("eYear"),
            mParam2!!.getInt("empg"),
            mParam2!!.getInt("price"))

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

                val gasPrice = 2.8 * i / gasCar.mpg
                val electricPrice = 1.15 * i / eCar.mpg

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

        val carDescripton = "if you drove the " + eCar.model + " instead of the " + gasCar.model
        subtitle.text = carDescripton

        return inf
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (listener != null) {
            listener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DataVisualization.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DataVisualization().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

private fun String.getInt(s: String): Int {
    throw RuntimeException("Stub!")

}

private fun String.getString(s: String): String? {
    throw RuntimeException("Stub!")
}

