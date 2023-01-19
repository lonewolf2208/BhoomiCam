package com.example.bhoomicam.view.auth

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentChooseLangBinding
import com.example.bhoomicam.view.utils.Datastore
import kotlinx.coroutines.launch
import java.util.*


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
            findNavController().navigate(R.id.action_chooseLang_to_loginFragment)
        }
        binding.button3.setOnClickListener {
            var locale = Locale("hi")
            Locale.setDefault(locale)
            var configuration=context?.resources?.configuration
            configuration?.locale=locale
            context?.resources?.updateConfiguration(configuration,context?.resources?.displayMetrics)
//            activity?.recreate()
            var editor= Datastore(requireContext())
            lifecycleScope.launch {
                editor.saveUserDetails("lang", "hi")
            }
            findNavController().navigate(R.id.action_chooseLang_to_loginFragment)
        }
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_chooseLang_to_loginFragment)
        }
        return binding.root
    }


}