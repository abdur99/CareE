package com.example.care_e

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewModel (application: Application) : AndroidViewModel(application) {

    var destinationMarker = MutableLiveData<Marker>()
    var currLocation = MutableLiveData<LatLng>()



    var firstname = MutableLiveData<String>()
    var lastname = MutableLiveData<String>()

    var GasCar = MutableLiveData<Car>()
    var ECar = MutableLiveData<ElectricCar>()

    var GasCarModel = MutableLiveData<String>()
    var GasCarMake = MutableLiveData<String>()
    var GasCarYear = MutableLiveData<Int>()
    var GasCarMPG = MutableLiveData<Int>()

    var ECarModel = MutableLiveData<String>()
    var EcarMake = MutableLiveData<String>()
    var ECakeYear = MutableLiveData<Int>()
    var ECarMPG = MutableLiveData<Int>()

    var UserUID = MutableLiveData<String>()

    var TripDistanceNumber = MutableLiveData<Double>()
    var TripDistanceString = MutableLiveData<String>()

    var TripCostNumber = MutableLiveData<Double>()
    var TripCostString = MutableLiveData<String>()

    var TripEmission = MutableLiveData<Double>()
    var TripEmissionString = MutableLiveData<String>()

    var DailySaving = MutableLiveData<Double>()
    var MonthlySaving = MutableLiveData<Double>()
    var YearlySaving = MutableLiveData<Double>()

    var Trips = MutableLiveData<ArrayList<TripInfo>>()

    var database = MutableLiveData<DatabaseReference>()

    init {
        GasCar.value = Car("Blake", "Bike", 1999, 21)
        ECar.value = ElectricCar("Blake", "Bike", 1999, 21, 21000)
        TripDistanceNumber.value = 0.0
        TripCostNumber.value = 0.0
        TripEmission.value = 0.0
        Trips.value = ArrayList()

        database.value = FirebaseDatabase.getInstance().reference

        database.value?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                Trips.value!!.clear()

                p0.child("Users").children.forEach {
                    it.getValue(TripInfo::class.java)?.let {
                        Trips.value?.add(it)
                    }
                }
                listener?.updateRecycler()
            }
        })


    }

    fun saveCar(car : Car) {
        var uid = UserUID.value
        val ref = FirebaseDatabase.getInstance().getReference("Users/$uid/Car")
        ref.setValue(car).addOnSuccessListener {
            Log.d("Register Activity", "Add fav  teams to database")
        }
    }

    fun addCarInfo(car: Car) {
        GasCar.value = car
    }

    fun saveTrip() {
        var uid = UserUID.value
        var trip = TripInfo()
        trip.tripcost = TripCostNumber.value!!
        trip.tripdistance = TripDistanceNumber.value!!
        trip.tripemission = TripEmission.value!!

        val ref = FirebaseDatabase.getInstance().getReference("Users/$uid/Trip")
        ref.setValue(trip).addOnSuccessListener {
            Log.d("Register Activity", "Add fav  teams to database")
        }
    }

//    fun deleteTrip(trip : TripInfo) {
//        database.value?.child("Users")?.child(Trip.userId)?.setValue(null)
//        database.value?.child("Users")?.push()
//    }

//    fun retrieveCars() {
//
//        val menuListener = object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                dataSnapshot.children.mapNotNullTo(GasCar.value!!) {
//                    it.getValue(GasCar::class.java)
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                println("loadPost:onCancelled ${databaseError.toException()}")
//            }
//        }
//        database.value?.child("Users/${UserUID.value!!}/Car")?.addListenerForSingleValueEvent(menuListener)
//
//    }

    var listener: DataChangedListener? = null

    interface DataChangedListener {
        fun updateRecycler()

    }

}