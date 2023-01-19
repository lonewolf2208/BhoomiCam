package com.example.bhoomicam.view.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.view.dashboard.DashboardActivity
import com.example.bhoomicam.view.utils.Datastore
import kotlinx.coroutines.launch
import java.util.*

class SplashScreen : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var datastore=Datastore(requireContext())
        Handler().postDelayed(
            {
                lifecycleScope.launch {
                    var lang=datastore.getUserDetails("lang")
                    if (lang=="hi")
                    {
                        var locale = Locale("hi")
                        Locale.setDefault(locale)
                        var configuration=context?.resources?.configuration
                        configuration?.locale=locale
                        context?.resources?.updateConfiguration(configuration,context?.resources?.displayMetrics)
                    }
                    if (datastore.isLogin()) {
                        startActivity(Intent(requireContext(),DashboardActivity::class.java))
                    } else {
                        findNavController().navigate(R.id.action_splashScreen_to_chooseLang)
                    }
                }
        },3000 )
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }



}