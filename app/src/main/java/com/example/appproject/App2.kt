package com.example.appproject

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class App2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app2)

        val goToMainActivityButton = findViewById<Button>(R.id.bt2)
        goToMainActivityButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val goToApp3Button = findViewById<Button>(R.id.ba2)
        goToApp3Button.setOnClickListener {
            val intent = Intent(this, App3::class.java)
            startActivity(intent)
        }
    }

}