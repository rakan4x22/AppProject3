package com.example.appproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner


class MainActivity : AppCompatActivity() {

    var selectedValue: String? = null
    var swain = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.sp1)

        val Button12 = findViewById<Button>(R.id.b123)
        Button12.setOnClickListener {
            selectedValue = null
        }

        val a = findViewById<Button>(R.id.bt1)
        a.setOnClickListener {
            finish()
        }


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedValue = spinner.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedValue = null
            }
        }

        if (selectedValue == null) {
            swain = false
        } else {
            swain = true
        }

        if (swain) {
            // Start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // Start App2
            val intent = Intent(this, App2::class.java)
            startActivity(intent)
        }
    }
}
