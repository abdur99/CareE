package com.example.care_e

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.firebase.database.DatabaseReference

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

    var database = MutableLiveData<DatabaseReference>()

    init {
        GasCar.value = Car("Blake", "Bike", 1999, 21)
        ECar.value = ElectricCar("Blake", "Bike", 1999, 21, 21000)
        TripDistanceNumber.value = 0.0
        TripCostNumber.value = 0.0
        TripEmission.value = 0.0

        database.value?.addValueEventListener()


    }

    fun addCarInfo(car: Car) {
        GasCar.value = car
    }
}