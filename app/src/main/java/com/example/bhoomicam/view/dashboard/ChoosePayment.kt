package com.example.bhoomicam.view.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentChoosePaymentBinding

class ChoosePayment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding= FragmentChoosePaymentBinding.inflate(inflater, container, false)
        binding.ViaUpi.setOnClickListener {
            startActivity(Intent(requireContext(), PaymentActivity::class.java))
        }
        return binding.root
    }

}