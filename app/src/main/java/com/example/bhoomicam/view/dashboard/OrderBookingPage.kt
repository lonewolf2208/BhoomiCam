package com.example.bhoomicam.view.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentOrderBookingPageBinding


class OrderBookingPage : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding =FragmentOrderBookingPageBinding.inflate(inflater, container, false)
        binding.BookDroneServices.setOnClickListener {
            startActivity(Intent(requireContext(),PaymentActivity::class.java))

        }
        binding.BookDroneServices.setOnClickListener {
            findNavController().navigate(R.id.action_orderBookingPage_to_orderDetailsPage)
        }
        return binding.root
    }



}