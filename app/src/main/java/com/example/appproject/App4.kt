package com.example.appproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class App4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app4)
        val a = findViewById<Button>(R.id.btt1)
        a.setOnClickListener {
            val intent = Intent(this, App3::class.java)
            startActivity(intent)
        }
    }
}