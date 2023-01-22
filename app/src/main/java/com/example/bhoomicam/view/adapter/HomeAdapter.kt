package com.example.bhoomicam.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.CardHomeLayoutBinding
import com.example.bhoomicam.view.model.drone_services_data

class HomeAdapter(var data:List<drone_services_data>?):RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    var clickListener:ClickListener?=null
    interface ClickListener{
        fun OnClick(position:Int)
    }
    fun onClickListener( clickListener:ClickListener)
    {
        this.clickListener=clickListener
    }

    inner class ViewHolder(var binding:CardHomeLayoutBinding):RecyclerView.ViewHolder(binding.root) {

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
        val binding:CardHomeLayoutBinding =
            DataBindingUtil.inflate(layoutInflater,
                R.layout.card_home_layout,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.captionservices.text= data!![holder.adapterPosition].text.toString()
        holder.binding.datahome.text= data!![holder.adapterPosition].Content.toString()
        holder.binding.imageHome.setBackgroundResource(data!![holder.adapterPosition].image)
    }

    override fun getItemCount(): Int {
      return data!!.size

    }
}