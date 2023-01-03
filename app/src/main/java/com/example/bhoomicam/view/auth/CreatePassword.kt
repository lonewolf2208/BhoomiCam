package com.example.bhoomicam.view.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentCreatePasswordBinding
import com.example.bhoomicam.view.dashboard.DashboardActivity


class CreatePassword : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        binding.ConfirmCreatePassword.setOnClickListener {
            startActivity(Intent(requireContext(),DashboardActivity::class.java))
        }

        return binding.root
    }


}