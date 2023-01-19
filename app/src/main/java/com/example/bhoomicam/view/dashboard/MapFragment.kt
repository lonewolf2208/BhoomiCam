package com.example.bhoomicam.view.dashboard

import android.Manifest

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import com.example.bhoomicam.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(),OnMapReadyCallback{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

           }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var binding = FragmentMapBinding.inflate(inflater, container, false)
        // Initialize map fragment
        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(com.example.bhoomicam.R.id.google_map) as SupportMapFragment?

        // Async map

        // Async map
//        supportActionBar?.title = "Map Location Activity"

        supportMapFragment?.getMapAsync(this)

        return binding.root

    }


    override fun onMapReady(mMap: GoogleMap) {

//
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
//        mMap.getUiSettings().setCompassEnabled(true);
//        //Initialize Google Play Services
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(requireContext(),
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//                mMap.setMyLocationEnabled(true);
//                val location = LatLng(MapActivity.latitudeTextView.toDouble(), MapActivity.longitTextView.toDouble())
//                mMap.addMarker(
//                    MarkerOptions().position(location)
//                        .title("Current Location") // below line is use to add custom marker on our map.
//
//                        )
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
//            }
//        }
    }




}