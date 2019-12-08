package com.example.care_e

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import java.io.IOException
import java.util.*


class ChooseECar : Fragment() , AdapterView.OnItemSelectedListener {

    private var ebtnSubmit: Button? = null

    lateinit var mContext: Context

    internal var eCars = ArrayList<ElectricCar>()


    internal lateinit var eMakeAdapter: ArrayAdapter<String>
    internal lateinit var eModelAdapter: ArrayAdapter<String>
    internal lateinit var eYearAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        var chooseecarfrag = inflater.inflate(R.layout.fragment_choose_ecar, container, false)

        try {
            val ecarStream = getResources().openRawResource(R.raw.ecardata)
            eCars = DataParser.readJSON2(ecarStream)
            ecarStream.close()
        } catch (oops: IOException) {
            Log.e("FileError", oops.toString())
        }

        //ELECTRIC CARS

        val eMake = ArrayList<String>(0)
        val eModel = ArrayList<String>(0)
        val eYear = ArrayList<String>(0)

        for (eCar in eCars) {
            eMake.add(eCar.make!!)
            eModel.add(eCar.model!!)
            eYear.add(Integer.toString(eCar.year))
        }

        val eMakeSet = HashSet<String>()
        eMakeSet.addAll(eMake)
        eMake.clear()
        eMake.addAll(eMakeSet)

        val eModelSet = HashSet<String>()
        eModelSet.addAll(eModel)
        eModel.clear()
        eModel.addAll(eModelSet)

        val eYearSet = HashSet<String>()
        eYearSet.addAll(eYear)
        eYear.clear()
        eYear.addAll(eYearSet)

        eMakeAdapter = ArrayAdapter(this.requireContext(), R.layout.support_simple_spinner_dropdown_item, eMake)
        eModelAdapter = ArrayAdapter(this.requireContext(), R.layout.support_simple_spinner_dropdown_item, eModel)
        eYearAdapter = ArrayAdapter(this.requireContext(), R.layout.support_simple_spinner_dropdown_item, eYear)

        val eMakeSpinner = chooseecarfrag.findViewById<View>(R.id.eMakeSpinner) as Spinner
        eMakeSpinner.adapter = eMakeAdapter
        eMakeSpinner.onItemSelectedListener = this
        val eModelSpinner = chooseecarfrag.findViewById<View>(R.id.eModelSpinner) as Spinner
        eModelSpinner.adapter = eModelAdapter
        eModelSpinner.onItemSelectedListener = this
        val eYearSpinner = chooseecarfrag.findViewById<View>(R.id.eYearSpinner) as Spinner
        eYearSpinner.adapter = eYearAdapter
        eYearSpinner.onItemSelectedListener = this

        ebtnSubmit = chooseecarfrag.findViewById<View>(R.id.esbutton) as Button

        ebtnSubmit!!.setOnClickListener {
            var theTwo = ElectricCar("eBlake", "Bike", 2048, 151, 420)

            for (eCar in eCars) {
                if (eMakeSpinner.selectedItem == eCar.make && eModelSpinner.selectedItem == eCar.model) {
                    theTwo = eCar
                }
            }
            Log.e("VROOOOOM", theTwo.toString())

            val ecarBundle = Bundle()

            ecarBundle.putString("eMake", theTwo.make)
            ecarBundle.putString("eModel", theTwo.model)
            ecarBundle.putInt("eYear", theTwo.year)
            ecarBundle.putInt("empg", theTwo.mpg)
            ecarBundle.putInt("price", theTwo.pricePoint)

            Log.e("carmake", theTwo.make!!)
            findNavController().navigate(R.id.action_chooseECar_to_homeScreen, ecarBundle)
        }

        // Inflate the layout for this fragment
        return chooseecarfrag
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        val makes: Set<String>
        val models: MutableSet<String>
        val years: MutableSet<String>

        when (parent.id) {
            R.id.eMakeSpinner -> {

                val eMake = eMakeAdapter.getItem(pos)

                eModelAdapter.clear()
                eYearAdapter.clear()

                models = HashSet(0)
                years = HashSet(0)


                for (car in eCars) {
                    if (eMake == car.make) {
                        models.add(car.model!!)
                        years.add(Integer.toString(car.year))
                    }
                }

                eModelAdapter.addAll(models)
                eYearAdapter.addAll(years)

                Log.e("Options", "make")
            }
            R.id.eModelSpinner -> {

                val eModel = eMakeAdapter.getItem(pos)

                eYearAdapter.clear()

                years = HashSet(0)

                for (car in eCars) {
                    if (eModel == car.make) {
                        years.add(Integer.toString(car.year))
                    }
                }

                eYearAdapter.addAll(years)
            }

        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

        eMakeAdapter.clear()
        eModelAdapter.clear()
        eYearAdapter.clear()

        val makes = HashSet<String>(0)
        val models = HashSet<String>(0)
        val years = HashSet<String>(0)


        for (eCar in eCars) {
            makes.add(eCar.make!!)
            models.add(eCar.model!!)
            years.add(Integer.toString(eCar.year))
        }

        eMakeAdapter.addAll(makes)
        eModelAdapter.addAll(models)
        eYearAdapter.addAll(years)

    }


}
