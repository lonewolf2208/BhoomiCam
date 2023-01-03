package com.example.bhoomicam.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentHomePageBinding
import com.example.bhoomicam.view.adapter.DroneServicesAdapter
import com.example.bhoomicam.view.adapter.HomeAdapter
import com.example.bhoomicam.view.model.drone_services_data

class HomePage : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding= FragmentHomePageBinding.inflate(inflater, container, false)
        var layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        var data = listOf<drone_services_data>(
            drone_services_data(R.drawable.drone_home,"Try Our Drone Services"),
            drone_services_data(R.drawable.home2,"See all your Orders"),
            drone_services_data(R.drawable.ic_drone_soil_analysis,"Crop Ananlysis")
        )
        binding.recyclerViewHome.layoutManager=layoutManager
        var adapter= HomeAdapter(data)
        binding.recyclerViewHome.adapter=adapter
        return binding.root
    }


}