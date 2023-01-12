package com.example.bhoomicam.view.another

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.google.firebase.FirebaseApp


class global_application_file : Application() {

    override fun onCreate(){
        super.onCreate()
        // this initialize is for global initialization for fire base
        FirebaseApp.initializeApp(applicationContext)
    }

    override fun attachBaseContext(base: Context?){
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}