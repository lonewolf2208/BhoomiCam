package com.example.bhoomicam.view.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentDroneServiesPageBinding
import com.example.bhoomicam.view.adapter.DroneServicesAdapter
import com.example.bhoomicam.view.model.drone_services_data
import com.google.android.material.bottomsheet.BottomSheetDialog

class DroneServiesPage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding=FragmentDroneServiesPageBinding.inflate(inflater, container, false)
        var data = listOf<drone_services_data>(drone_services_data(R.drawable.ic_drone_spray,"Drone based Fertilizer, Pesticides, weedicides spray services"),
            drone_services_data(R.drawable.ic_drone_group_48064,"Drone based Evidence for Insurance Companies"),
            drone_services_data(R.drawable.ic_drone_soil_analysis,"Drone based soil and field analysis"),
            drone_services_data(R.drawable.drone_health_monitoring,"Drone based yield estimation, geo-fencing, crop growth, health analysis")
        )
        var layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.RecylerViewDroneServices.layoutManager=layoutManager
        var adapter= DroneServicesAdapter(data)
        binding.RecylerViewDroneServices.adapter=adapter
        adapter.onClickListener(object : DroneServicesAdapter.ClickListener {
            override fun OnClick(position: Int) {
                val bottomSheet = BottomSheetDialog(requireContext())
                val dialogView =
                    LayoutInflater.from(requireContext())
                        .inflate(R.layout.drone_data, null)
                dialogView.findViewById<Button>(R.id.button10).setOnClickListener {
                   findNavController().navigate(R.id.orderBookingPage)
                    bottomSheet.cancel()
                }
                bottomSheet.setContentView(dialogView)
                bottomSheet.show()
            }
        })
        return binding.root
    }


}