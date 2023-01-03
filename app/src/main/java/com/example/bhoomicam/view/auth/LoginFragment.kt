package com.example.bhoomicam.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentLoginBinding
import com.example.bhoomicam.view.dashboard.DashboardActivity


class LoginFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.SignUpPage.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUp)
        }
        binding.ForgotPasswordPage.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPassword)
        }
        binding.LoginButton.setOnClickListener {
            startActivity(Intent(requireContext(),DashboardActivity::class.java))
        }
        return binding.root
    }


}