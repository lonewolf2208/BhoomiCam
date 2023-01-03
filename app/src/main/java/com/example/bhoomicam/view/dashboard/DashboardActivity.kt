package com.example.bhoomicam.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding=ActivityDashboardBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_dashboard) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        binding.bottomNavigationDashboard.setupWithNavController(navController)
        setContentView(binding.root)
    }
}