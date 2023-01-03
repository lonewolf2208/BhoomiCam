package com.example.bhoomicam.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.CardHomeLayoutBinding
import com.example.bhoomicam.databinding.OrdersCardBinding
import com.example.bhoomicam.view.model.drone_services_data

class OrdersAdapter():RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    var clickListener:ClickListener?=null
    interface ClickListener{
        fun OnClick(position:Int)
    }
    fun onClickListener( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }

    inner class ViewHolder(var binding:OrdersCardBinding):RecyclerView.ViewHolder(binding.root) {

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
        val binding:OrdersCardBinding =
            DataBindingUtil.inflate(layoutInflater,
                R.layout.orders_card,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
//        holder.binding.captionservices.text= data!![holder.adapterPosition].text.toString()
////        holder.binding.datahome.text= data!![holder.adapterPosition].Content.toString()
//        holder.binding.imageHome.setBackgroundResource(data!![holder.adapterPosition].image)
    }

    override fun getItemCount(): Int {
      return 1

    }
}