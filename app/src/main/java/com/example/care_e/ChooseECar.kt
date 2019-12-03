package com.example.care_e

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import java.io.IOException
import java.util.HashSet


class ChooseECar : Fragment() {

//    private var btnSubmit: Button? = null
//
//
//    internal var eCars = ArrayList<ElectricCar>()
//    internal lateinit var eMakeAdapter: ArrayAdapter<String>
//    internal lateinit var eModelAdapter: ArrayAdapter<String>
//    internal lateinit var eYearAdapter: ArrayAdapter<String>
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_car_info, container, false)
//
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        eCars = java.util.ArrayList(0)
//
//        try {
//
//            val inputStream= resources.openRawResource(R.raw.ecardata)
//            eCars = DataParser.readJSON2(inputStream)
//
//        }
//        catch (oops: IOException) {
//            Log.e("FileError", oops.toString())
//        }
//
//        // electric cars
//        val eMake = java.util.ArrayList<String>(0)
//        val eModel = java.util.ArrayList<String>(0)
//        val eYear = java.util.ArrayList<String>(0)
//
//        for (eCar in eCars) {
//            eMake.add(eCar.make!!)
//            eModel.add(eCar.model!!)
//            eYear.add(Integer.toString(eCar.year))
//        }
//
//        val eMakeSet = HashSet<String>()
//        eMakeSet.addAll(eMake)
//        eMake.clear()
//        eMake.addAll(eMakeSet)
//
//        val eModelSet = HashSet<String>()
//        eModelSet.addAll(eModel)
//        eModel.clear()
//        eModel.addAll(eModelSet)
//
//        val eYearSet = HashSet<String>()
//        eYearSet.addAll(eYear)
//        eYear.clear()
//        eYear.addAll(eYearSet)
//
////        eMakeAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, eMake)
////        eModelAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, eModel)
////        eYearAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, eYear)
////
////        val eMakeSpinner = findViewById<View>(R.id.electricMakeSpinner) as Spinner
////        eMakeSpinner.adapter = eMakeAdapter
////        eMakeSpinner.onItemSelectedListener = this
////        val eModelSpinner = findViewById<View>(R.id.electricModelSpinner) as Spinner
////        eModelSpinner.adapter = eModelAdapter
////        eModelSpinner.onItemSelectedListener = this
////        val eYearSpinner = findViewById<View>(R.id.electricYearSpinner) as Spinner
////        eYearSpinner.adapter = eYearAdapter
////        eYearSpinner.onItemSelectedListener = this
////
////        btnSubmit = findViewById<View>(R.id.sbutton) as Button
//
////faaiz
//
//
//
//    }



}
