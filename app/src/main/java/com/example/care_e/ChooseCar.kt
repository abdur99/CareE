package com.example.care_e

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_car_info.*
import kotlinx.android.synthetic.main.fragment_choose_car.*
import java.io.IOException
import java.util.*


class ChooseCar : Fragment() , AdapterView.OnItemSelectedListener {

    private var btnSubmit: Button? = null

    lateinit var mContext: Context

    internal var cars = ArrayList<Car>()

   lateinit var model: ViewModel

    internal lateinit var makeAdapter: ArrayAdapter<String>
    internal lateinit var modelAdapter: ArrayAdapter<String>
    internal lateinit var yearAdapter: ArrayAdapter<String>
    var firstname : String? = ""
    var lastname : String? = ""
    var email : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        if ((arguments?.getString("FirstName")) != null) { firstname = arguments?.getString("FirstName").toString() }
        if ((arguments?.getString("LastName")) != null) { lastname = arguments?.getString("LastName").toString() }
        if ((arguments?.getString("Email")) != null) { email = arguments?.getString("Email").toString() }


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

            var carBundle = Bundle()
            carBundle.putString("make", theOne.make)
            carBundle.putString("model", theOne.model)
            carBundle.putInt("year", theOne.year)
            carBundle.putInt("mpg", theOne.mpg)
            carBundle.putString("FirstName", firstname)
            carBundle.putString("LastName", lastname)
            carBundle.putString("Email", email)

            Log.e("carmake", theOne.make!!)
            findNavController().navigate(R.id.action_navigation_choose_car_to_chooseECar, carBundle)
        }

        // Inflate the layout for this fragment
        return  choosecarfrag
    }

    private fun saveCar(new_car: Car){


//        for(team in new_car){
//            //dataManager.favoritesList.value!!.add(team)
//            //dataManager.user.value!!.fav_teams.add(team)
//        }

        var user = UserandCarInfo()

        val uid = FirebaseAuth.getInstance().uid?: model.usern.value!!
        user.uid = uid
        val ref = FirebaseDatabase.getInstance().getReference("Users/$uid/Favorite Teams")
        ref.setValue(model.usern.value!!).addOnSuccessListener {
            Log.d("Register Activity", "Add fav  teams to database")
        }

    }


    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        val makes: Set<String>
        val models: MutableSet<String>
        val years: MutableSet<String>

        Toast.makeText(activity, "TestLMAO", Toast.LENGTH_SHORT).show()


        when (parent.id) {
            R.id.MakeSpinner -> {

                val make = makeAdapter.getItem(pos)
                if(make == "Ferrari"){
                    car_image.setImageResource(R.drawable.ferrari488)
                }
                if(make == "Acura"){
                    car_image.setImageResource(R.drawable.acura_rlx)
                }
                if(make == "Audi"){
                    car_image.setImageResource(R.drawable.audir8)
                }
                if(make == "Aston Martin"){
                    car_image.setImageResource(R.drawable.astinmartinvintage)
                }
                if(make == "Bentley"){
                    car_image.setImageResource(R.drawable.bentlybentyga)
                }
                if(make == "BMW"){
                    car_image.setImageResource(R.drawable.bmw750i)
                }
                if(make == "Chevy"){
                    car_image.setImageResource(R.drawable.chovette)
                }
                if(make == "Chrysler"){
                    car_image.setImageResource(R.drawable.pacifica)
                }
                if(make == "Dodge"){
                    car_image.setImageResource(R.drawable.charger)
                }
                if(make == "FIAT"){
                    car_image.setImageResource(R.drawable.i500l)
                }
                if(make == "Ford"){
                    car_image.setImageResource(R.drawable.mustang)
                }
                if(make == "GMC"){
                    car_image.setImageResource(R.drawable.sierra)
                }
                if(make == "Honda"){
                    car_image.setImageResource(R.drawable.accord)
                }
               
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

                if(model == "RLX" && make.equals("Acura")){
                    car_image.setImageResource(R.drawable.acura_rlx)
                }
                if(model == "NSX" && make.equals("Acura")){
                    car_image.setImageResource(R.drawable.acura_rlx)
                }

                    // model
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
