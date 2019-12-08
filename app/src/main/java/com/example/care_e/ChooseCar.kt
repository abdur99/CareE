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
import java.io.IOException
import java.util.*


class ChooseCar : Fragment() , AdapterView.OnItemSelectedListener {

    private var btnSubmit: Button? = null

    lateinit var mContext: Context

    internal var cars = ArrayList<Car>()


    internal lateinit var makeAdapter: ArrayAdapter<String>
    internal lateinit var modelAdapter: ArrayAdapter<String>
    internal lateinit var yearAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        var choosecarfrag = inflater.inflate(R.layout.fragment_choose_car, container, false)

        try {
            val carStream = getResources().openRawResource(R.raw.cardata)
            cars = DataParser.readJSON(carStream)
            carStream.close()
        } catch (oops: IOException) {
            Log.e("FileError", oops.toString())
        }

        //GAS CARS
        val make = ArrayList<String>(0)
        val model = ArrayList<String>(0)
        val year = ArrayList<String>(0)

        for (car in cars) {
            make.add(car.make!!)
            model.add(car.model!!)
            year.add(Integer.toString(car.year))
        }

        val makeSet = HashSet<String>()
        makeSet.addAll(make)
        make.clear()
        make.addAll(makeSet)

        val modelSet = HashSet<String>()
        modelSet.addAll(model)
        model.clear()
        model.addAll(modelSet)

        val yearSet = HashSet<String>()
        yearSet.addAll(year)
        year.clear()
        year.addAll(yearSet)

        makeAdapter =
            ArrayAdapter(this.requireContext(), R.layout.support_simple_spinner_dropdown_item, make)
        modelAdapter = ArrayAdapter(
            this.requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            model
        )
        yearAdapter =
            ArrayAdapter(this.requireContext(), R.layout.support_simple_spinner_dropdown_item, year)

        val makeSpinner = choosecarfrag.findViewById<View>(R.id.MakeSpinner) as Spinner
        makeSpinner.adapter = makeAdapter
        makeSpinner.onItemSelectedListener = this
        val modelSpinner = choosecarfrag.findViewById<View>(R.id.ModelSpinner) as Spinner
        modelSpinner.adapter = modelAdapter
        modelSpinner.onItemSelectedListener = this
        val yearSpinner = choosecarfrag.findViewById<View>(R.id.YearSpinner) as Spinner
        yearSpinner.adapter = yearAdapter
        yearSpinner.onItemSelectedListener = this

        btnSubmit = choosecarfrag.findViewById<View>(R.id.sbutton) as Button

        btnSubmit!!.setOnClickListener {
            var theOne = Car("Blake", "Kart", 2018, 195)

            for (car in cars) {
                if (makeSpinner.selectedItem == car.make && modelSpinner.selectedItem == car.model) {
                    theOne = car
                }
            }
            Log.e("VROOOOOM", theOne.toString())

            val carBundle = Bundle()
            carBundle.putString("make", theOne.make)
            carBundle.putString("model", theOne.model)
            carBundle.putInt("year", theOne.year)
            carBundle.putInt("mpg", theOne.mpg)

            Log.e("carmake", theOne.make!!)
            val goToMain = Intent(mContext, MainActivity::class.java)

            goToMain.putExtra("Cars", carBundle)
            startActivity(goToMain)
        }

        // Inflate the layout for this fragment
        return  choosecarfrag
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        val makes: Set<String>
        val models: MutableSet<String>
        val years: MutableSet<String>

        when (parent.id) {
            R.id.MakeSpinner -> {

                val make = makeAdapter.getItem(pos)

                modelAdapter.clear()
                yearAdapter.clear()

                models = HashSet(0)
                years = HashSet(0)


                for (car in cars) {
                    if (make == car.make) {
                        models.add(car.model!!)
                        years.add(Integer.toString(car.year))
                    }
                }

                modelAdapter.addAll(models)
                yearAdapter.addAll(years)

                Log.e("Options", "make")
            }
            R.id.ModelSpinner -> {

                val model = makeAdapter.getItem(pos)

                yearAdapter.clear()

                years = HashSet(0)

                for (car in cars) {
                    if (model == car.make) {
                        years.add(Integer.toString(car.year))
                    }
                }

                yearAdapter.addAll(years)
            }

        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

        makeAdapter.clear()
        modelAdapter.clear()
        yearAdapter.clear()

        val makes = HashSet<String>(0)
        val models = HashSet<String>(0)
        val years = HashSet<String>(0)


        for (car in cars) {
            makes.add(car.make!!)
            models.add(car.model!!)
            years.add(Integer.toString(car.year))
        }

        makeAdapter.addAll(makes)
        modelAdapter.addAll(models)
        yearAdapter.addAll(years)

    }

}
