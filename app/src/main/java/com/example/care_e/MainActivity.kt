package com.example.care_e

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_trip_record, R.id.navigation_car_info, R.id.navigation_previous_trips
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//        map.uiSettings.isZoomControlsEnabled = true
//        map.setOnMarkerClickListener(this)
//
//        if(location != null){
//            var latLng = LatLng(location.latitude, location.longitude)
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f)) // 2-21 levels of zoom
//        }
//
//
//        if (!checkPermission()) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                100
//            )
//        } else {
//            gpsManager.register()
//        }
//    }
//
//    fun checkPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    fun updateCurrentLocation(location : Location?){
//        if(location != null){
//            var latLng = LatLng(location.latitude, location.longitude)
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f)) // 2-21 levels of zoom
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//            exitProcess(0)
//        } else {
//            gpsManager.register()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        gpsManager.unregister()
//    }
//
//    override fun onResume(){
//        super.onResume()
//        if(checkPermission() && ::map.isInitialized){
//            gpsManager.register()
//        }
//    }

}
