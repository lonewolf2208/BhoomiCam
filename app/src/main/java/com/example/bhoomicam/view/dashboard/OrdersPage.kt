package com.example.bhoomicam.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentOrdersPageBinding
import com.example.bhoomicam.view.adapter.HomeAdapter
import com.example.bhoomicam.view.adapter.OrdersAdapter
import com.example.bhoomicam.view.model.drone_services_data


class OrdersPage : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding= FragmentOrdersPageBinding.inflate(inflater, container, false)
        var layoutManager= LinearLayoutManager(requireContext())

        binding.orderRecyclerView.layoutManager=layoutManager
        var adapter= OrdersAdapter()
        binding.orderRecyclerView.adapter=adapter
        return binding.root
    }


}