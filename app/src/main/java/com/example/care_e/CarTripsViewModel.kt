package com.example.care_e

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.Marker

class CarTripsViewModel (application: Application) : AndroidViewModel(application) {
    var destinationMarker = MutableLiveData<Marker>()

}