package com.example.bhoomicam.view.dashboard

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentOrderBookingPageBinding
import java.util.*


class OrderBookingPage : Fragment(),DatePickerDialog.OnDateSetListener  {

    lateinit var binding:FragmentOrderBookingPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderBookingPageBinding.inflate(inflater, container, false)
        binding.BookDroneServices.setOnClickListener {
         findNavController().navigate(R.id.action_orderBookingPage_to_choosePayment)
        }
        binding.DateBooking.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)
            var datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
            datePickerDialog.show()
        }
        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var Selectedmonth = month.toString()
        var SelectedDate = dayOfMonth.toString()
        if (month / 10 == 0) {
            Selectedmonth = "0" + (month + 1).toString()
        }
        if (dayOfMonth / 10 == 0) {
            Toast.makeText(context, "dsadasdadsad", Toast.LENGTH_LONG).show()
            SelectedDate = "0" + (dayOfMonth).toString()
        }
        var date =
            year.toString() + "-" + (Selectedmonth).toString() + "-" + SelectedDate.toString()
        binding.DateBooking.setText(date)
    }
}
