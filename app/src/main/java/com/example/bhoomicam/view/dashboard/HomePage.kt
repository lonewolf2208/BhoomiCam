package com.example.bhoomicam.view.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentHomePageBinding
import com.example.bhoomicam.view.adapter.HomeAdapter
import com.example.bhoomicam.view.another.login_page
import com.example.bhoomicam.view.model.drone_services_data
import com.example.bhoomicam.view.utils.Datastore
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

class HomePage : Fragment() {
    private val WEATHER_API_KEY = "3b0667770f1f4a13a86ca58625cead27"
    var WEATHER_DETAILS = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding= FragmentHomePageBinding.inflate(inflater, container, false)
        var layoutManager= StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        var data = listOf<drone_services_data>(
            drone_services_data(R.drawable.drone_home,"Try Our Drone Services"),
            drone_services_data(R.drawable.home2,"See all your Orders"),
            drone_services_data(R.drawable.ic_drone_soil_analysis,"Crop Ananlysis"),
            drone_services_data(R.drawable.home2,"Login Page")
        )
        var sdf = SimpleDateFormat("EEEE")
        val d = Date()
        var req: String = sdf.format(d) +" , "
        sdf= SimpleDateFormat("dd")
        req+=sdf.format(d)+" "
        sdf=SimpleDateFormat("MMMM")
        req+=sdf.format(d)
//        Log.d("asda",req)
        binding.dateHome.text=req
        lifecycleScope.launch {
            var datastore=Datastore(requireContext())
            var lat=datastore.getUserDetails("latitude") ?: "29.854263"
            var long = datastore.getUserDetails("longitude") ?:"77.888000"

            val weather_url =
                "https://api.weatherbit.io/v2.0/current?lat=${lat}&lon=${long}&key=$WEATHER_API_KEY"
            val make_request_ = Volley.newRequestQueue(requireContext())

            // this will request the weather report from the API
            val string_req = StringRequest(
                Request.Method.GET , weather_url ,
                { response_ ->
//                    Toast.makeText(requireContext(),"Data downloaded successfully",Toast.LENGTH_SHORT).show()

                    // converting string response to json
                    val object_ = JSONObject(response_)
                    val arr_ = object_.getJSONArray("data")

                    val object_2 = arr_.getJSONObject(0)

                    // making String to display

                    binding.tempHome.text=object_2.getString("temp")
                    binding.cityName.text=object_2.getString("city_name")
                    WEATHER_DETAILS = object_2.getString("temp") + "deg Celsius , of the city :" + object_2.getString("city_name")
//                load_fragment(Home_page(context_,WEATHER_DETAILS))
//                    Toast.makeText(requireContext(), WEATHER_DETAILS, Toast.LENGTH_SHORT).show()
                },
                {
                    Toast.makeText(requireContext(), "Temperature didn't get", Toast.LENGTH_SHORT).show()
                })


            make_request_.add(string_req)
        }
        binding.recyclerViewHome.layoutManager=layoutManager
        var adapter= HomeAdapter(data)
        binding.recyclerViewHome.adapter=adapter
        adapter.onClickListener(object : HomeAdapter.ClickListener {
            override fun OnClick(position: Int) {
                if(position==3)
                {
                    startActivity(Intent( requireContext(),login_page::class.java))
                }
                if(position==2)
                {
                    findNavController().navigate(R.id.action_homePage_to_cropAnalysis)
                }
            }
        })
        return binding.root
    }



}