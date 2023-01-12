package com.example.bhoomicam.view.another.location_access

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bhoomicam.R


class splach_activity : AppCompatActivity() {
    private lateinit var handler : android.os.Handler
    private var isNetworkLocation : Boolean = false
    private var isGPSLocation : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = android.os.Handler()
        setContentView(R.layout.location_access)
        StartAnimation()

        val mListener : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(mListener!=null){
            isGPSLocation = mListener.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetworkLocation = mListener.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }


        handler.post {
            if(isGPSLocation==true){
                val intent = Intent(this, map_activity::class.java)
                intent.putExtra("provider", LocationManager.GPS_PROVIDER)
                startActivity(intent)
                finish()
            }
            else if (isNetworkLocation){
                val intent = Intent(this,map_activity::class.java)
                intent.putExtra("provider", LocationManager.NETWORK_PROVIDER)
                startActivity(intent)
                finish()
            } else {
                PermissionUtils.LocationSettingDialog.newInstance().show(supportFragmentManager, "Setting")
            }
        }
    }


    //  Animation for showing locations of the user
    private fun StartAnimation(){
//        var anim = AnimationUtils.loadAnimation(this,R.anim.alpha)
////        anim.reset()
//        val l : LinearLayout = findViewById(R.id.lin_lay)
//        l.clearAnimation()
//        l.startAnimation(anim)
////        anim = AnimationUtils.loadAnimation(this,R.anim.translate)
//        anim.reset()
//        val tv = findViewById<TextView>(R.id.logo)
//        tv.clearAnimation()
//        tv.startAnimation(anim)
    }


    override protected fun onRestart(){
        super.onRestart()
        startActivity(Intent(this, splach_activity::class.java))
        finish()
    }
}