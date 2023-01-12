package com.example.bhoomicam.view.another

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bhoomicam.R
import com.example.bhoomicam.view.another.adapters.show_images_adapter
import com.example.bhoomicam.view.another.adapters.crop_type_adapter
import com.example.face_detection.database.universal_database
import com.example.face_detection.model.*
import com.example.face_detection.send_farmer_data_firebase
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.firebase.database.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class crop_detail : AppCompatActivity() , LocationListener {

    private var current_photo_path : File ? = null           // this will activate when the camera is clicked and the path of phto will stored
    private lateinit var size_field_edit_text : EditText
    private lateinit var context : Context
    private lateinit var growth_stage : RelativeLayout
    private lateinit var growth_stage_text_view : TextView
    private lateinit var growth_stage_tick : ImageView

    private lateinit var crop_health_condition : RelativeLayout
    private lateinit var crop_health_condition_text : TextView
    private lateinit var health_condition_tick : ImageView

    private lateinit var cover_on_ground : RelativeLayout
    private lateinit var cover_crop_percentage_text : TextView
    private lateinit var percentage_tick_image : ImageView

    private lateinit var soil_condition : RelativeLayout
    private lateinit var condition_of_soil_text : TextView
    private lateinit var condition_soil_tick : ImageView

    private lateinit var save_all_info_ : Button
    private lateinit var ALL_CROP_DATA : crop_detail_model
    private lateinit var parsed_past_data : farmer_personal_model

    private lateinit var ALL_UNIVERSAL_DATA : all_data_model
    private lateinit var send_to_server : send_farmer_data_firebase

    private lateinit var CURRENT_GPS : current_gps_store_model

    // for location listener
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2

    // show all captured photo
    private lateinit var recycle_view : RecyclerView
    private lateinit var camera_click : ImageView
    val CAMARA_ACCESS = 11
    private lateinit var adapter : show_images_adapter
    private lateinit var send_to_firebase : send_to_firebase
    private lateinit var receiver_ : BroadcastReceiver
    private lateinit var final_field_image_model : feild_image_data_model

    // fro the real time database of the firebase
    private lateinit var database : DatabaseReference
    private lateinit var firebaseDatabase : FirebaseDatabase
    private lateinit var list_crop_image : ArrayList<String>

    // ids of the all drop down logo
    private lateinit var drop_logo_1 : ImageView
    private lateinit var drop_logo_2 : ImageView
    private lateinit var drop_logo_3 : ImageView
    private lateinit var drop_logo_4 : ImageView

    // for drop down of the crop season
    private lateinit var select_season_ : RelativeLayout

    // selections of theseason
    private var SEASON_SELECTION = ""

    // after selection of menu drop down
    private lateinit var growth_state_selected_show : TextView
    private lateinit var health_selected_show : TextView
    private lateinit var show_selected_soild_condition : TextView
    private lateinit var cover_crop_selected_show : TextView

    private lateinit var crop_type_recyle_view : RecyclerView

    private lateinit var selected_season_show : TextView
    private var kharif_crops = ArrayList<crop_type_image_model>()
    private var rabi_crops = ArrayList<crop_type_image_model>()
    private var zaid_crops = ArrayList<crop_type_image_model>()

    // harvesting date and collecting date
    private lateinit var harvesting_date_layout : RelativeLayout
    private lateinit var cutting_date_layout : RelativeLayout
    private lateinit var show_selected_harvesting_date : TextView
    private lateinit var show_selected_cutting_date : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crop_detail)

        ALL_CROP_DATA = crop_detail_model("","","","","","")

        // initializing the current gps
        CURRENT_GPS = current_gps_store_model("","")

        list_crop_image = ArrayList<String>()
        final_field_image_model = feild_image_data_model("",list_crop_image,"",CURRENT_GPS.latitute,CURRENT_GPS.longitute)

        context = this
        send_to_server = send_farmer_data_firebase(this)
        send_to_firebase = send_to_firebase(this)

        val previous_page_data = intent.getStringExtra("FARMER_DATA").toString()
        if(previous_page_data!="")parsed_past_data = jacksonObjectMapper().readValue<farmer_personal_model>(previous_page_data)

        // initializing the real time datbase
        firebaseDatabase = FirebaseDatabase.getInstance()
        database = firebaseDatabase.getReference("${parsed_past_data.khasra_number}")

        show_selected_harvesting_date = findViewById(R.id.show_selected_harvesting_date)
        show_selected_cutting_date = findViewById(R.id.show_cutting_date)

        growth_state_selected_show = findViewById(R.id.growth_state_selected_show)
        health_selected_show = findViewById(R.id.crop_health_selected_show)
        show_selected_soild_condition = findViewById(R.id.show_selected_soild_condition)
        cover_crop_selected_show = findViewById(R.id.cover_crop_selected_show)

        harvesting_date_layout = findViewById(R.id.harvesting_date_layout_)
        cutting_date_layout = findViewById(R.id.cutting_date_layout)

        crop_type_recyle_view = findViewById(R.id.crop_type_recyle_view)

        selected_season_show = findViewById(R.id.selected_season_show)

        size_field_edit_text = findViewById(R.id.size_field_edit_text)
        select_season_ = findViewById(R.id.select_season_)
        growth_stage = findViewById(R.id.growth_stage)
        growth_stage_text_view = findViewById(R.id.growth_stage_text_view)
        growth_stage_tick  = findViewById(R.id.growth_stage_tick)

        crop_health_condition = findViewById(R.id.crop_health_condition)
        crop_health_condition_text = findViewById(R.id.crop_health_condition_text)
        health_condition_tick  = findViewById(R.id.health_condition_tick)

        cover_on_ground = findViewById(R.id.cover_on_ground)
        cover_crop_percentage_text = findViewById(R.id.cover_crop_percentage_text)
        percentage_tick_image  = findViewById(R.id.percentage_tick_image)

        soil_condition = findViewById(R.id.soil_condition)
        condition_of_soil_text = findViewById(R.id.condition_of_soil_text)
        condition_soil_tick  = findViewById(R.id.condition_soil_tick)

        recycle_view = findViewById(R.id.show_photo_recycle_view)
        save_all_info_ = findViewById(R.id.save_all_info_)

        camera_click = findViewById(R.id.capture_)

        drop_logo_1 = findViewById(R.id.drop_down_logo)
        drop_logo_2 = findViewById(R.id.drop_down_logo_2)
        drop_logo_3 = findViewById(R.id.drop_down_logo_3)
        drop_logo_4 = findViewById(R.id.drop_down_logo_4)


        // selecting season
        select_season_.setOnClickListener {
            val pop_menu = PopupMenu(this,select_season_)
            pop_menu.menuInflater.inflate(R.menu.type_of_season,pop_menu.menu)

            pop_menu.setOnMenuItemClickListener { it->
                if(it.itemId==R.id.kharif)SEASON_SELECTION = "Kharif"
                if(it.itemId==R.id.Rabi)SEASON_SELECTION  = "Rabi"
                if(it.itemId==R.id.Zaid)SEASON_SELECTION = "Zaid"

                // visibility of recycle View
                crop_type_recyle_view.visibility = View.VISIBLE

                selected_season_show.visibility = View.VISIBLE
                selected_season_show.setText(SEASON_SELECTION)

                // selected crop should display in the recycleView
                if(SEASON_SELECTION=="Kharif"){
                    kharif_crop_insert()    // this will insert the kharif crops

                    val layout_manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    crop_type_recyle_view.layoutManager = layout_manager
                    crop_type_recyle_view.setHasFixedSize(true)
                    val adapter_kharif = crop_type_adapter(this,kharif_crops)
                    crop_type_recyle_view.adapter = adapter_kharif
                }
                if(SEASON_SELECTION=="Rabi"){
                    rabi_crops_initialization()

                    val layout_manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    crop_type_recyle_view.layoutManager = layout_manager
                    crop_type_recyle_view.setHasFixedSize(true)
                    val adapter_rabi  = crop_type_adapter(this,rabi_crops)
                    crop_type_recyle_view.adapter = adapter_rabi
                }
                if(SEASON_SELECTION=="Zaid"){
                    zaid_crop_initialization()

                    val layout_manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    crop_type_recyle_view.layoutManager = layout_manager
                    crop_type_recyle_view.setHasFixedSize(true)
                    val adapter_zaid = crop_type_adapter(this,zaid_crops)
                    crop_type_recyle_view.adapter = adapter_zaid
                }

                true
            }

            pop_menu.show()
        }

        // for pop_up textview menu
        growth_stage.setOnClickListener {
            val pop_menu = PopupMenu(this,growth_stage)
            pop_menu.menuInflater.inflate(R.menu.crop_growth_condition,pop_menu.menu)

            pop_menu.setOnMenuItemClickListener {
                if(it.itemId==R.id.transplanting_)ALL_CROP_DATA.crop_growth_stage = "Transplanting"
                if(it.itemId==R.id.sowing_id)ALL_CROP_DATA.crop_growth_stage = "Sowing"
                if(it.itemId==R.id.tillering)ALL_CROP_DATA.crop_growth_stage = "Tillering"
                if(it.itemId==R.id.vegetative)ALL_CROP_DATA.crop_growth_stage = "Vegetative"
                if(it.itemId==R.id.flowering_)ALL_CROP_DATA.crop_growth_stage = "Flowering"
                if(it.itemId==R.id.maturity_id)ALL_CROP_DATA.crop_growth_stage = "Maturity Stage"

                Log.d("","mMMMMMMMMMMMMMMMMMMMMMMMMMMMMMenu growth_stage is:${ALL_CROP_DATA.crop_growth_stage}")
//                growth_stage_text_view.visibility = View.GONE
//                growth_stage_tick.visibility = View.VISIBLE
//                drop_logo_1.visibility = View.GONE

                // visibility of selected text
                growth_state_selected_show.visibility = View.VISIBLE
                growth_state_selected_show.setText(ALL_CROP_DATA.crop_growth_stage)

                true
            }

            pop_menu.show()
        }

        // pop up for crop health condition
        crop_health_condition.setOnClickListener {
            val pop_menu = PopupMenu(this,crop_health_condition)
            pop_menu.menuInflater.inflate(R.menu.crop_helth_condition,pop_menu.menu)

            pop_menu.setOnMenuItemClickListener {
                if(it.itemId==R.id.good_)ALL_CROP_DATA.crop_health_condition = "Good"
                if(it.itemId==R.id.Average)ALL_CROP_DATA.crop_health_condition = "Average"
                if(it.itemId==R.id.Poor)ALL_CROP_DATA.crop_health_condition = "Poor"

//                Log.d("","mMMMMMMMMMMMMMMMMMMMMMMMMMMMMMenu health_conditions is:${ALL_CROP_DATA.crop_health_condition}")
//                crop_health_condition_text.visibility = View.GONE
//                health_condition_tick.visibility = View.VISIBLE
//                drop_logo_2.visibility = View.GONE

                health_selected_show.visibility = View.VISIBLE
                health_selected_show.setText(ALL_CROP_DATA.crop_health_condition)

                true
            }

            pop_menu.show()
        }

        // cover on ground
        cover_on_ground.setOnClickListener {
            val pop_menu = PopupMenu(this,cover_on_ground)
            pop_menu.menuInflater.inflate(R.menu.crop_cover_on_ground,pop_menu.menu)

            pop_menu.setOnMenuItemClickListener {
                if(it.itemId==R.id.less)ALL_CROP_DATA.crop_cover_in_ground_percentage = "Less"
                if(it.itemId==R.id.large)ALL_CROP_DATA.crop_cover_in_ground_percentage = "Large"

                Log.d("","mMMMMMMMMMMMMMMMMMMMMMMMMMMMMMenu percentage is:${ALL_CROP_DATA.crop_cover_in_ground_percentage}")
//                cover_crop_percentage_text.visibility = View.GONE
//                percentage_tick_image.visibility = View.VISIBLE
//                drop_logo_3.visibility = View.GONE

                cover_crop_selected_show.visibility = View.VISIBLE
                cover_crop_selected_show.setText(ALL_CROP_DATA.crop_cover_in_ground_percentage)

                true
            }
            pop_menu.show()
        }

        /// for soil condition
        soil_condition.setOnClickListener {
            val pop_menu = PopupMenu(this,soil_condition)
            pop_menu.menuInflater.inflate(R.menu.soil_condition,pop_menu.menu)

            pop_menu.setOnMenuItemClickListener {
                if(it.itemId==R.id.Moist)ALL_CROP_DATA.soil_condition_ = "Moist"
                if(it.itemId==R.id.Dry)ALL_CROP_DATA.soil_condition_ = "Dry"
                if(it.itemId==R.id.Flooded)ALL_CROP_DATA.soil_condition_ = "Flooded"
                if(it.itemId==R.id.ideal_condition)ALL_CROP_DATA.soil_condition_ = "Ideal Condition"

                Log.d("","mMMMMMMMMMMMMMMMMMMMMMMMMMMMMMenu soilconditions is:${ALL_CROP_DATA.soil_condition_}")
//                condition_of_soil_text.visibility = View.GONE
//                condition_soil_tick.visibility = View.VISIBLE
//                drop_logo_4.visibility = View.GONE

                show_selected_soild_condition.visibility = View.VISIBLE
                show_selected_soild_condition.setText(ALL_CROP_DATA.soil_condition_)

                true
            }
            pop_menu.show()
        }

        // harevesting date selection
        /* get harvesting date */
        harvesting_date_layout.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.date_selection)

            val date_picker = dialog.findViewById<DatePicker>(R.id.date_picker)
            val confirm = dialog.findViewById<Button>(R.id.confirm)

            var selected_year =""
            var selected_month = ""
            var selected_day = ""

            val today = Calendar.getInstance()
            date_picker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)){ view, year, month, day ->
                selected_day = "$day"
                selected_month = "$month"
                selected_year = "$year"
                Toast.makeText(context,"You selected date : $selected_day:$selected_month:${selected_year}",Toast.LENGTH_SHORT).show()
                show_selected_harvesting_date.setText("$selected_day:$selected_month:${selected_year}")
                show_selected_harvesting_date.setText("$selected_day:$selected_month:${selected_year}")
            }

            // this will execute when the date is confirmed
            confirm.setOnClickListener{
                dialog.dismiss()
            }
            dialog.show()
        }

        // cutting date
        cutting_date_layout.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.date_selection)

            val date_picker = dialog.findViewById<DatePicker>(R.id.date_picker)
            val confirm = dialog.findViewById<Button>(R.id.confirm)

            var selected_year = ""
            var selected_month = ""
            var selected_day = ""

            val today = Calendar.getInstance()
            date_picker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) { view, year, month, day ->
                selected_day = "$day"
                selected_month = "$month"
                selected_year = "$year"
                Toast.makeText(context,"You selected date : $selected_day:$selected_month:${selected_year}",Toast.LENGTH_SHORT).show()
                show_selected_cutting_date.visibility = View.VISIBLE
                show_selected_cutting_date.setText("$selected_day:$selected_month:${selected_year}")
            }
            dialog.dismiss()

            // this will execute when the date is confirmed
            confirm.setOnClickListener {}
            dialog.show()
        }

        camera_click.setOnClickListener {
            val file_name = "cypher_${System.currentTimeMillis()}"
            val storage_dir =  getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            try {
                val image_file = File.createTempFile(file_name,".jpg",storage_dir)
                current_photo_path = image_file
                val image_uri = FileProvider.getUriForFile(this,"com.example.bhoomicam.view.another.login_page",image_file)

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri)
                startActivityForResult(intent,CAMARA_ACCESS)

            } catch(e : Exception){
                Log.d("","eeeeeeeeeeeeeeeeeeeerror is:${e}")
            }
        }


        list_crop_image = ArrayList<String>()
        save_all_info_.setOnClickListener {
                // update to database and because all data are saved at the first page
                // send to the server

               val string_form_data = jacksonObjectMapper().writeValueAsString(final_field_image_model)
                ALL_UNIVERSAL_DATA = all_data_model(
                    parsed_past_data.name_,
                    parsed_past_data.district_,
                    parsed_past_data.village_,
                    parsed_past_data.state,
                    parsed_past_data.mobile_number_,
                    parsed_past_data.adhar_number_,
                    parsed_past_data.farmer_photo,
                    ALL_CROP_DATA.types_of_crops,
                    ALL_CROP_DATA.size_of_field,
                    ALL_CROP_DATA.crop_growth_stage,
                    ALL_CROP_DATA.crop_health_condition,
                    ALL_CROP_DATA.crop_cover_in_ground_percentage,
                    ALL_CROP_DATA.soil_condition_,
                    string_form_data
                )

                Log.d("","TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTtype of crop=${ALL_CROP_DATA.types_of_crops} &&&&& size_of_crop_=${ALL_CROP_DATA.size_of_field}")

                val db = universal_database(this , null)
                db.save_data ( parsed_past_data.name_,
                    parsed_past_data.district_,
                    parsed_past_data.village_,
                    parsed_past_data.state,
                    parsed_past_data.mobile_number_,
                    parsed_past_data.adhar_number_,
                    parsed_past_data.farmer_photo,
                    ALL_CROP_DATA.types_of_crops,
                    ALL_CROP_DATA.size_of_field,
                    ALL_CROP_DATA.crop_growth_stage,
                    ALL_CROP_DATA.crop_health_condition,
                    ALL_CROP_DATA.crop_cover_in_ground_percentage,
                    ALL_CROP_DATA.soil_condition_,
                    string_form_data )

              send_to_real_time()
        }

        get_location()

        // adapting of recycleView
        val layout_manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recycle_view.layoutManager = layout_manager
        recycle_view.setHasFixedSize(true)
        adapter = show_images_adapter(this)
        recycle_view.adapter = adapter
    }


    fun get_location(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }


    override fun onLocationChanged(location : Location){
        tvGpsLocation = findViewById(R.id.show_gps_location)
        tvGpsLocation.text = "Current GPS Latitude: " + location.latitude + " , Longitude: " + location.longitude
        CURRENT_GPS = current_gps_store_model(location.latitude.toString(),location.longitude.toString())
    }


      override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){

        Log.d("","TTTTTTTTTTTTTTTTTTTTThe request permission result is sending is initiating!!")

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == locationPermissionCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

     /*
   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

       super.onActivityResult(requestCode, resultCode, data)
        // Accessing camera
        if(resultCode == Activity.RESULT_OK && requestCode == CAMARA_ACCESS){

            try {
                val date_ = SimpleDateFormat("dd-MM-yyyy").format(Date())
                val time_ = SimpleDateFormat("hh:mm a").format(Date())
                val final_date = "$date_ , $time_"
                val uri_ = FileProvider.getUriForFile(this, "com.example.bhoomicam.view.another.login_page", current_photo_path!!)
                var transfering_data = feild_image_data_model(uri_.toString(),"",final_date,CURRENT_GPS.latitute,CURRENT_GPS.longitute)

                Log.d("","CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCrop link is:${uri_}")

                val dialog_ = Dialog(this)
                dialog_.setContentView(R.layout.uploading_dialog_sheet)

                val show_image = dialog_.findViewById<ImageView>(R.id.first_photo_show)
                val progress_bar = dialog_.findViewById<ProgressBar>(R.id.progress_bar)
                val text_view_upload = dialog_.findViewById<TextView>(R.id.text_show_upload)

                // fetching the image
                Glide.with(this).load(uri_).apply{ RequestOptions().centerCrop() }.into(show_image)

                // receive the uploading status of the photo
                receiver_ = object : BroadcastReceiver(){

                    override fun onReceive(p0: Context?, data : Intent?){
                        var result_ = data!!.getStringExtra("PROGRESS")
                        var image_uri_ = data.getStringExtra("DOWNLOAD_URI").toString()    // after uploading download uri

                        var progress_ = 0
                        if(result_!="" && result_!=null)progress_ = result_.toInt()
                        progress_bar.progress = progress_
                        progress_bar.max = 100
                        text_view_upload.setText("$progress_%")

                        if(image_uri_!="" && image_uri_!="null" && progress_==100){
                            Log.d("","TTTTTTTTTTTTTTTTTThe link is:${image_uri_}")
                            transfering_data.download_uri = image_uri_
                            Toast.makeText(context,"Upload of image completed!",Toast.LENGTH_SHORT).show()
                            dialog_.dismiss()
                        }
                    }
                }

                // registering roadcast for receiving uploading status
                LocalBroadcastManager.getInstance(this).registerReceiver(receiver_, IntentFilter("UPLOAD"))

                adapter.set_data(transfering_data)
                send_to_firebase.upload_image(uri_.toString(),"${parsed_past_data.adhar_number_}_${System.currentTimeMillis()}")
                dialog_.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_.show()

            } catch (e: java.lang.Exception) { }
        }
    }
   */

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("","LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl  request_code=:${requestCode}")

        if(resultCode==Activity.RESULT_OK && requestCode==CAMARA_ACCESS){
            Log.d("","LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl:-----------")

                val date_ = SimpleDateFormat("dd-MM-yyyy").format(Date())
                val time_ = SimpleDateFormat("hh:mm a").format(Date())
                val final_date = "$date_ , $time_"
                val uri_ = FileProvider.getUriForFile(this, "com.example.bhoomicam.view.another.login_page", current_photo_path!!)
                var transfering_data = feild_image_data_model(uri_.toString(),list_crop_image,final_date,CURRENT_GPS.latitute,CURRENT_GPS.longitute)

                final_field_image_model = transfering_data
                Log.d("","CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCrop link is:${uri_}")

                val dialog_ = Dialog(this)
                dialog_.setContentView(R.layout.uploading_dialog_sheet)

                val show_image = dialog_.findViewById<ImageView>(R.id.first_photo_show)
                val progress_bar = dialog_.findViewById<ProgressBar>(R.id.progress_bar)
                val text_view_upload = dialog_.findViewById<TextView>(R.id.text_show_upload)

                // fetching the image
                Glide.with(this).load(uri_).apply{ RequestOptions().centerCrop() }.into(show_image)

                // receive the uploading status of the photo
                receiver_ = object : BroadcastReceiver(){

                    override fun onReceive(p0: Context?, data : Intent?){
                        var result_ = data!!.getStringExtra("PROGRESS")
                        var image_uri_ = data.getStringExtra("DOWNLOAD_URI").toString()    // after uploading download uri

                        var progress_ = 0
                        if(result_!="" && result_!=null)progress_ = result_.toInt()
                        progress_bar.progress = progress_
                        progress_bar.max = 100
                        text_view_upload.setText("$progress_%")

                        if(image_uri_!="" && image_uri_!="null" && progress_==100){
                            Log.d("","TTTTTTTTTTTTTTTTTThe link is:${image_uri_}")
//                            transfering_data.download_uri = image_uri_
                            list_crop_image.add(image_uri_)
                            transfering_data.list_download_uri = list_crop_image
                            final_field_image_model = transfering_data
                            Toast.makeText(context,"Upload of image completed!",Toast.LENGTH_SHORT).show()
                            dialog_.dismiss()
                        }
                    }
                }

                // registering broadcast for receiving uploading status
                LocalBroadcastManager.getInstance(this).registerReceiver(receiver_, IntentFilter("UPLOAD"))

                adapter.set_data(transfering_data)
                send_to_firebase.upload_image(uri_.toString(),"${parsed_past_data.adhar_number_}_${System.currentTimeMillis()}")
                dialog_.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_.show()
        }
   }

    override fun onProviderEnabled(provider: String){
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

     // this functions will send data to server
    fun send_to_real_time(){
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                database.setValue(ALL_UNIVERSAL_DATA)
//                Toast.makeText(context,"Data is added to the server",Toast.LENGTH_SHORT).show()
                // show the dialog text to the user
                val dialog = Dialog(context)
                dialog.setContentView(R.layout.sended_ok_view)

                val ok_button = dialog.findViewById<RelativeLayout>(R.id.ok_layout)
                ok_button.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }

            override fun onCancelled(error: DatabaseError){
                Toast.makeText(context,"EEEEEEEEEEEEEEEEEEEerror in sending the value to server",Toast.LENGTH_SHORT).show()
            }
        })
    }

    // kharif crops initialization
    fun kharif_crop_insert(){
        kharif_crops.add(crop_type_image_model("Bajra",R.drawable.bajra_image,false))
        kharif_crops.add(crop_type_image_model("Jowar",R.drawable.jowar_image,false))
        kharif_crops.add(crop_type_image_model("Maize",R.drawable.maize_image_,false))
        kharif_crops.add(crop_type_image_model("Millet",R.drawable.millate_image,false))
        kharif_crops.add(crop_type_image_model("Rice",R.drawable.rice_image,false))
        kharif_crops.add(crop_type_image_model("Soybean",R.drawable.soybeans,false))
    }


    // Rabi crop initiaslization
    fun rabi_crops_initialization(){
        rabi_crops.add(crop_type_image_model("Barley",R.drawable.barley,false))
        rabi_crops.add(crop_type_image_model("Gram",R.drawable.gram,false))
        rabi_crops.add(crop_type_image_model("Rapessed",R.drawable.rapessed,false))
        rabi_crops.add(crop_type_image_model("Mustered",R.drawable.mastered_oil,false))
        rabi_crops.add(crop_type_image_model("Oat",R.drawable.oat_,false))
        rabi_crops.add(crop_type_image_model("Wheat",R.drawable.wheat_1,false))
        rabi_crops.add(crop_type_image_model("Bajra",R.drawable.bajra_image,false))
    }

    // zaid season selection
    fun zaid_crop_initialization(){
        zaid_crops.add(crop_type_image_model("Mustered",R.drawable.mastered_oil,false))
        zaid_crops.add(crop_type_image_model("Soyabin",R.drawable.soybeans,false))
        zaid_crops.add(crop_type_image_model("Pumpkin",R.drawable.pumpkin,false))
        zaid_crops.add(crop_type_image_model("Ground nuts",R.drawable.ground_nuts,false))

    }
}
