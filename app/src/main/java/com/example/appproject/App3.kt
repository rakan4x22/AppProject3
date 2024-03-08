package com.example.appproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class App3 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app3)

        val a = findViewById<Button>(R.id.bt5)
        a.setOnClickListener {
            val intent = Intent(this, App2::class.java)
            startActivity(intent)
        }

        val b = findViewById<Button>(R.id.bot1)
        b.setOnClickListener {
            val intent = Intent(this, App4::class.java)
            startActivity(intent)
        }

        val c = findViewById<Button>(R.id.bot2)
        c.setOnClickListener {
            val intent = Intent(this, App5::class.java)
            startActivity(intent)
        }

        val d = findViewById<Button>(R.id.bot4)
        d.setOnClickListener {
            val intent = Intent(this, App6::class.java)
            startActivity(intent)
        }
    }

}