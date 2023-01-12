package com.example.bhoomicam.view.another

import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bhoomicam.R
import com.example.face_detection.model.farmer_personal_model
import com.example.face_detection.model.image_container_model
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.firebase.FirebaseApp
import java.io.File

class login_page : AppCompatActivity() {
    private lateinit var all_farmer_detail_ : farmer_personal_model
    private lateinit var next_button : Button
    private lateinit var take_photo : ImageView
    private var current_photo_path : File ? = null           // this will activate when the camera is clicked and the path of phto will stored
    val CAMARA_ACCESS = 100234
    private lateinit var image_upload : send_to_firebase
    private lateinit var receiver_ : BroadcastReceiver
    private lateinit var context_ : Context

    private lateinit var name_id_ : EditText
    private lateinit var district_edit_text : EditText
    private lateinit var village_edit_text : EditText
    private lateinit var state_edit_text : EditText

    private lateinit var number_edit_text : EditText
    private lateinit var adhar_card_number_edit_text : EditText
    private lateinit var camera_photo : String

    private lateinit var khasra_number : EditText


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        next_button = findViewById(R.id.next_button)
        take_photo  = findViewById(R.id.take_photo_)

        camera_photo = ""
        name_id_ = findViewById(R.id.name_id)
        district_edit_text  = findViewById(R.id.district_edit_text)
        village_edit_text = findViewById(R.id.village_edit_text)
        state_edit_text = findViewById(R.id.state_edit_text)
        number_edit_text = findViewById(R.id.number_edit_text)
        adhar_card_number_edit_text = findViewById(R.id.adhar_card_number_edit_text)
        khasra_number = findViewById(R.id.khasra_edit_text)

        context_ = this

        // initializing the firebase app
        FirebaseApp.initializeApp(this)

        all_farmer_detail_ = farmer_personal_model(khasra_number.text.toString(),"","","","","","","")

        next_button.setOnClickListener {

            if( name_id_.text.toString()=="" ||
                district_edit_text.text.toString()=="" ||
                village_edit_text.text.toString()==""||
                state_edit_text.text.toString()=="" ||
                number_edit_text.text.toString()=="" ||
                adhar_card_number_edit_text.text.toString()==""||
                khasra_number.text.toString()==""
            ){
                Toast.makeText(context_,"Please fill all the blanks",Toast.LENGTH_SHORT).show()
            }

            else{
                    all_farmer_detail_.name_ = name_id_.text.toString()
                    all_farmer_detail_.district_ = district_edit_text.text.toString()
                    all_farmer_detail_.village_ = village_edit_text.text.toString()
                    all_farmer_detail_.state = state_edit_text.text.toString()
                    all_farmer_detail_.mobile_number_ = number_edit_text.text.toString()
                    all_farmer_detail_.adhar_number_ = adhar_card_number_edit_text.text.toString()

                    val data_ = farmer_personal_model(
                        khasra_number.text.toString(),
                        all_farmer_detail_.name_,
                        all_farmer_detail_.district_,
                        all_farmer_detail_.village_,
                        all_farmer_detail_.state,
                        all_farmer_detail_.mobile_number_,
                        all_farmer_detail_.adhar_number_,
                        camera_photo )

                    val string_form_data = jacksonObjectMapper().writeValueAsString(data_)

                    val intent = Intent(this, crop_detail::class.java)
                    intent.putExtra("FARMER_DATA",string_form_data)
                    startActivity(intent)
            }
        }

        image_upload = send_to_firebase(this)

        take_photo.setOnClickListener {

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
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        // Accessing camera
        if (resultCode == Activity.RESULT_OK && requestCode == CAMARA_ACCESS){
            try {
                val uri_ = FileProvider.getUriForFile(this, "com.example.bhoomicam.view.another.login_page", current_photo_path!!);
                Log.d("", "CCCCCCCCCCCCCCCCCCCCCCamear is acccessed now!! and uri is:${uri_}")
                image_upload.upload_image(uri_.toString(),adhar_card_number_edit_text.text.toString())

                val dialog_ = Dialog(this)
                dialog_.setContentView(R.layout.uploading_dialog_sheet)

                val show_image = dialog_.findViewById<ImageView>(R.id.first_photo_show)
                val progress_bar = dialog_.findViewById<ProgressBar>(R.id.progress_bar)
                val text_view_upload = dialog_.findViewById<TextView>(R.id.text_show_upload)

                // fetching the image
                Glide.with(this).load(uri_).apply { RequestOptions().centerCrop() }.into(show_image)

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
                            val data_ = image_container_model(uri_.toString(),image_uri_)
                            camera_photo = jacksonObjectMapper().writeValueAsString(data_)
                            Toast.makeText(context_,"Upload of image completed!",Toast.LENGTH_SHORT).show()
                            dialog_.dismiss()
                        }
                    }
                }

                // registering roadcast for receiving uploading status
                LocalBroadcastManager.getInstance(this).registerReceiver(receiver_, IntentFilter("UPLOAD"))

                dialog_.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_.show()
            }

            catch (e:java.lang.Exception){}
        }
    }



    fun get_all_data(){

    }

}