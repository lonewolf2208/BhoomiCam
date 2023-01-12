package com.example.bhoomicam.view.another.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bhoomicam.R
import com.example.face_detection.model.feild_image_data_model

class show_images_adapter(private var context_ : Context) : RecyclerView.Adapter<show_images_adapter.DataAdapterViewHolder>() {

    var adapterData = ArrayList<feild_image_data_model>()

    fun set_data(data_ : feild_image_data_model){
        adapterData.add(data_)
        notifyDataSetChanged()
    }

    override fun getItemCount() : Int = adapterData.size

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        val supply_data = adapterData[position]
        holder.bind(supply_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val inflater = LayoutInflater.from(context_)
        val view = inflater.inflate(R.layout.photo_of_feild_ , parent,false)
        return DataAdapterViewHolder(view)
    }

    override fun getItemId(position: Int): Long = position.toLong()


    inner class DataAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(data_ : feild_image_data_model){
            val image_view = itemView.findViewById<ImageView>(R.id.first_photo_show)      // image showing layout
//            Picasso.with(context_).load(uri_).resize(600,600).centerCrop().into(image_view)
            Glide.with(context_).load(data_.local_uri).apply { RequestOptions().centerCrop() }.into(image_view)
        }
    }
}