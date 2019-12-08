package com.example.care_e

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_trip_record.*
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.system.exitProcess
import android.os.Build
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode


class TripRecord : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener, LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    lateinit var CarEViewModel : ViewModel

    internal lateinit var mLastLocation: Location
    internal var mCurrLocationMarker: Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest


    private var count2 : Int = 0
    lateinit var destmarker : Marker

    override fun onMapLongClick(p0: LatLng) {
        if (count2 == 0) {
            CarEViewModel.destinationMarker.value = mMap.addMarker(MarkerOptions().position(p0).title("Destination"))
            CarEViewModel.destinationMarker.value!!.isDraggable = true
            val URL = getDirectionURL(p0, latLng)
            Log.d("GoogleMap", "URL : $URL")
            GetDirection(URL).execute()
            count2++
            topText.text = (distanceCalculator(latLng, p0).toString())
        }
        else {
            Toast.makeText(activity, "You have already picked a destination", Toast.LENGTH_SHORT).show()
        }
    }




    private lateinit var topText: TextView


    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        gpsManager = GPSManager(this)
        gpsManager.register()

        mMap.isMyLocationEnabled = true
        buildGoogleApiClient()
        mMap.isTrafficEnabled = true
        mMap.isBuildingsEnabled = true
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMarkerDragListener(this)
        mMap.setOnMapLongClickListener {
            onMapLongClick(it)
        }

        if (CarEViewModel.destinationMarker.value != null) {
            mMap.addMarker(MarkerOptions().position(CarEViewModel.destinationMarker.value!!.position)).isDraggable = true
            val URL = getDirectionURL(CarEViewModel.destinationMarker.value!!.position, CarEViewModel.currLocation.value!!)
            Log.d("GoogleMap", "URL : $URL")
            GetDirection(URL).execute()
        }
        SearchButton.setOnClickListener{
            findNavController().navigate(R.id.action_global_navigation_trip_record)
        }
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
    }

    fun updateCurrentLocation(location : Location?){
        if(location != null){
            latLng = LatLng(location.latitude, location.longitude)
            CarEViewModel.currLocation.value = latLng
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    private lateinit var latLng: LatLng
    lateinit var mMap: GoogleMap
    lateinit var gpsManager : GPSManager
    private lateinit var SearchButton :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        //gpsManager = GPSManager(this)
        //gpsManager.register()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        CarEViewModel = activity?.run{
            ViewModelProviders.of(this).get(ViewModel::class.java)
        }?: throw Exception("Activity Invalid")
        // Inflate the layout for this fragment

        var tripview = inflater.inflate(R.layout.fragment_trip_record, container, false)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //gpsManager = GPSManager(this)
        //gpsManager.register()
        SearchButton = tripview.findViewById(R.id.searchbtn)

        topText = tripview.findViewById(R.id.distance)


        return tripview
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&key=AIzaSyABlLiEzEm8wMshR2JRNKcq995t8NMEiZw"
    }

    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
//                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
//                    path.add(startLatLng)
//                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }

    public fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }

    override fun onMarkerDragStart(marker : Marker) {
        topText.text = getString(R.string.on_marker_drag_start)
    }

    override fun onMarkerDragEnd(marker : Marker) {
        val URL = getDirectionURL(marker.position, latLng)
        Log.d("GoogleMap", "URL : $URL")
        GetDirection(URL).execute()
        topText.text = distanceCalculator(latLng, marker.position).toString()
    }

    override fun onMarkerDrag(marker : Marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 15f))
        topText.text = getString(R.string.on_marker_drag, marker.position.latitude, marker.position.longitude)
    }

    fun distanceCalculator(LatLng1: LatLng, LatLng2: LatLng): String {
        var startlongitude = LatLng1.longitude
        var startlatitude = LatLng1.latitude
        var destlongitude = LatLng2.longitude
        var destlatitude = LatLng2.latitude
        var theta = startlongitude - destlongitude;
        var dist = Math.sin(deg2rad(startlatitude)) * Math.sin(deg2rad(destlatitude)) + Math.cos(deg2rad(startlatitude)) * Math.cos(deg2rad(destlatitude)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        if (dist < 1) {
            return (BigDecimal(dist * 1000).setScale(0, RoundingMode.HALF_EVEN)).toString() + " Meters"
        }
        else {
            return (BigDecimal(dist).setScale(3, RoundingMode.HALF_EVEN)).toString() + " Kilometers"
        }
    }

    fun deg2rad(deg : Double): Double {
        return (deg * Math.PI / 180.0);
    }

    fun rad2deg(rad : Double): Double {
        return (rad * 180.0 / Math.PI);
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this.requireActivity())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build()
        mGoogleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {

        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
        }
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onLocationChanged(location: Location) {

        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }
        //Place current location marker
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)

        //move map camera
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
        }

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }

    fun searchLocation(view: View) {
        val locationSearch:EditText = view.findViewById<EditText>(R.id.searchloc)
        lateinit var location: String
        location = locationSearch.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == "") {
            Toast.makeText(activity,"provide location",Toast.LENGTH_SHORT).show()
        }
        else{
            val geoCoder = Geocoder(activity)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
            mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(activity, address.latitude.toString() + " " + address.longitude, Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        //gpsManager.unregister()
    }

    override fun onResume(){
        super.onResume()
        //gpsManager.register()
    }

}
