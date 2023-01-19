package com.example.bhoomicam.view.dashboard

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.ActivityDashboardBinding
import com.example.bhoomicam.view.utils.Datastore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding=ActivityDashboardBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_dashboard) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottomNavigationDashboard.setupWithNavController(navController)
        binding.include.imageView6.setOnClickListener {
            lifecycleScope.launch{
                var datastore= Datastore(this@DashboardActivity)
                datastore.changeLoginState(false)
                finishAffinity()
            }
        }
        setContentView(binding.root)
    }
    override fun onBackPressed() {
        when (findNavController(R.id.fragment_container_dashboard).currentDestination?.id) {
            R.id.homePage -> alertBox()
            else -> super.onBackPressed()
        }
    }
    private fun alertBox()
    {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Quit App")
            .setMessage("Are you sure you want to leave the App?")
            .setPositiveButton("No"){dialog,id->dialog.cancel()}
            .setNegativeButton("Yes"){dialog,id->finishAffinity()}
        builder.show()
    }


}