package com.example.bhoomicam.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.CardDroneServicesBinding
import com.example.bhoomicam.view.model.drone_services_data

class DroneServicesAdapter(var data:List<drone_services_data>?):RecyclerView.Adapter<DroneServicesAdapter.ViewHolder>() {
    var clickListener:ClickListener?=null
    interface ClickListener{
        fun OnClick(position:Int)
    }
    fun onClickListener( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }

    inner class ViewHolder(var binding:CardDroneServicesBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickListener?.OnClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val binding:CardDroneServicesBinding =
            DataBindingUtil.inflate(layoutInflater,
                R.layout.card_drone_services,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.textDrone.text= data!![holder.adapterPosition].text.toString()
        holder.binding.imagedrone.setBackgroundResource(data!![holder.adapterPosition].image)
    }

    override fun getItemCount(): Int {
      return data!!.size

    }
}