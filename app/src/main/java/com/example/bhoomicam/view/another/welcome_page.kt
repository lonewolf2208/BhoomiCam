package com.example.bhoomicam.view.another

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.bhoomicam.R

class welcome_page :AppCompatActivity() {
    private lateinit var context : Context
    private lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page_)

        handler = Handler()
        context = this

        handler.postDelayed({

            val intent = Intent(context, select_language_::class.java)
            startActivity(intent)
            finish()

        },1400)

    }
}