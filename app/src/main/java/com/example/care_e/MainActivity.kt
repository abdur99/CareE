package com.example.care_e

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), TripRecord.OnFragmentInteractionListener{
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var firstname : String? = intent.getStringExtra("FirstName")
        var lastname : String? = intent.getStringExtra("LastName")
        var type : String? = intent.getStringExtra("Type")
        var email : String? = intent.getStringExtra("Email")

        var infobundle = Bundle()
        infobundle.putString("FirstName", firstname)
        infobundle.putString("LastName", lastname)
        infobundle.putString("Email", email)

        if (type == "SignUp") {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_navigation_choose_car, infobundle)
        }
        else {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_chooseECar, infobundle)
        }

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
