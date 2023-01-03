package com.example.bhoomicam.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentChooseLangBinding


class ChooseLang : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding= FragmentChooseLangBinding.inflate(inflater, container, false)
        binding.LangButton.setOnClickListener {
            findNavController().navigate(R.id.action_chooseLang_to_welcomePage)
        }
        return binding.root
    }


}