package com.example.bhoomicam.view.another

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toUri
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class send_to_firebase (val context : Context) {

    val firebase_storage = FirebaseStorage.getInstance().reference
    val path_ = "tstststststststststststststststststststs"
    val one_image = firebase_storage.child(path_)

    val baos = ByteArrayOutputStream()

    // this upload task is responsible for sending image data to server
    fun upload_image(_uri : String , adhar_number : String){
        val path_ = "images/$adhar_number"
        val one_image = firebase_storage.child(path_)
        val data_ = convert_from_uri_to_bytes(_uri)
        val upload_task = one_image.putBytes(data_)
        upload_task.addOnProgressListener { it->
            val transfered_byte = it.bytesTransferred
            val total_byte = it.totalByteCount
            val progress_ = (transfered_byte * 100) / total_byte
            Log.d("", "uuuuuuuuuuuuuuuuuuuploading status : ${progress_}%")

            val intent = Intent("UPLOAD")
            intent.putExtra("PROGRESS","$progress_")
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)

        }.addOnFailureListener { it->
            Log.d("","EEEEEEEEEEEEEEEEEEEEEErrrrrrrrrrrrrrrroor show is:${it}")
        }.addOnSuccessListener {
            it.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                Log.d("","LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLink is show is:${it}")
                val intent = Intent("UPLOAD")
                intent.putExtra("PROGRESS","100")
                intent.putExtra("DOWNLOAD_URI","$it")     //this will send the url to download this image
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
            }
        }
    }


    fun convert_from_uri_to_bytes(uri_ : String ) : ByteArray {
        val full_size_bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver,uri_.toUri()))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver,uri_.toUri())
        }

        val bitmap_: Bitmap = full_size_bitmap
        bitmap_.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data_ = baos.toByteArray()
        return data_
    }
}