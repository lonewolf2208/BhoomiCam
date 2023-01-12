package com.example.bhoomicam.view.another

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bhoomicam.R
import com.example.bhoomicam.view.another.location_access.splach_activity

class select_language_ : AppCompatActivity() {

    private lateinit var context : Context
    private lateinit var english_language_ : Button
    private lateinit var hindi_ : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.language_selection)
        context = this

        english_language_ = findViewById(R.id.english_button)
        hindi_ = findViewById(R.id.hindi_button)

        english_language_.setOnClickListener {
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }

        hindi_.setOnClickListener{
            val intent = Intent(this, splach_activity::class.java)
            startActivity(intent)
        }
    }
}