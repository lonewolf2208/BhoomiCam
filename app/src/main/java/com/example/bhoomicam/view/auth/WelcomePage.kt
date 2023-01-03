package com.example.bhoomicam.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentWelcomePageBinding


class WelcomePage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding=FragmentWelcomePageBinding.inflate(inflater, container, false)
        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_welcomePage_to_loginFragment)
        }
        binding.button5.setOnClickListener {
            findNavController().navigate(R.id.action_welcomePage_to_loginFragment)
        }
        return binding.root
    }


}