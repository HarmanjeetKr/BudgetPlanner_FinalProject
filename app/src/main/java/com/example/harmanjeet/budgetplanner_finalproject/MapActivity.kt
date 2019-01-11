package com.example.harmanjeet.budgetplanner_finalproject

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.maps.*

class MapActivity:FragmentActivity(),OnMapReadyCallback {
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val toronto = LatLng(43.6532, -79.3832)
        mMap!!.addMarker(MarkerOptions().position(toronto).title("Marker in Toronto"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(toronto))
    }

    fun Add_Location(view: View) {


        val lan_text = lan
        val lat_text = lat
        val location = center_name

        val s = lan_text.getText().toString()
        val lan = java.lang.Double.parseDouble(s)
        val lat = java.lang.Double.parseDouble(lat_text.getText().toString())


        val newCity = LatLng(lat, lan)
        mMap!!.addMarker(MarkerOptions().position(newCity).title(location.getText().toString()))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(newCity))


    }

}