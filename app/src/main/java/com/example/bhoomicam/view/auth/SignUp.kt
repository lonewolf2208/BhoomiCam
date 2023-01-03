package com.example.bhoomicam.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentSignUpBinding


class SignUp : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.SingnUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_createPassword)
        }
        binding.LogInPage.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_loginFragment)
        }
        return binding.root
    }


}