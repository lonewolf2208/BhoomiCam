package com.example.bhoomicam.view.another.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bhoomicam.R
import com.example.face_detection.model.crop_type_image_model

class crop_type_adapter( private val context_ : Context ,
                         private var adapterData : ArrayList<crop_type_image_model>
                       )
    : RecyclerView.Adapter<crop_type_adapter.DataAdapterViewHolder>() {


//    var all_crop_selected = ArrayList<String>() // this will select store all the selected crops

    override fun getItemCount() : Int = adapterData.size

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        val supply = adapterData[position]
        holder.bind(supply)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val inflater = LayoutInflater.from(context_)
        val view_ = inflater.inflate(R.layout.round_image_show,parent,false)
        return DataAdapterViewHolder(view_)
    }

    override fun getItemId(position: Int): Long  = position.toLong()


    inner class DataAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bind(data_ : crop_type_image_model){
            // set on click listener
            val whole_layout = itemView.findViewById<RelativeLayout>(R.id.whole_layout)
            val photo_show = itemView.findViewById<ImageView>(R.id.first_photo_show)
            val crop_name = itemView.findViewById<TextView>(R.id.crop_name_)

            // selected effect
            val tick_layout = itemView.findViewById<RelativeLayout>(R.id.selected_layout)
            val blur_layout_ = itemView.findViewById<ImageView>(R.id.selected_image_blur)

            Glide.with(context_).load(data_.crop_image).into(photo_show)
            crop_name.setText(data_.crop_name)

            if(data_.selection_status==false){
                Log.d("","KKKKKKKKKKKKKKKKKKKKKKKKKKKKKK now unselected this photo")
                tick_layout.visibility = View.GONE
                blur_layout_.visibility = View.GONE
            }
            if(data_.selection_status==true){
                tick_layout.visibility = View.VISIBLE
                blur_layout_.visibility = View.VISIBLE
            }

            whole_layout.setOnClickListener {
                if(data_.selection_status == true){
                    Log.d("","KKKKKKKKKKKKKKKKKKKKKKKKKKKKKK now unselected this photo")
                    data_.selection_status = false
                    adapterData[position] = data_
                    tick_layout.visibility = View.GONE
                    blur_layout_.visibility = View.GONE
                }
                if(data_.selection_status == false){
                    data_.selection_status = true
                    adapterData[position] = data_
                    tick_layout.visibility = View.VISIBLE
                    blur_layout_.visibility = View.VISIBLE
                }
            }

        }
    }
}