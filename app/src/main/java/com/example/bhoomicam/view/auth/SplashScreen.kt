package com.example.bhoomicam.view.auth

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R

class SplashScreen : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed(
            {
            findNavController().navigate(R.id.action_splashScreen_to_chooseLang)
        },3000 )
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }



}