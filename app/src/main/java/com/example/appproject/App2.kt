package com.example.appproject

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class App2 : AppCompatActivity() {

     var fajer:TextView?=null
     var zuhr:TextView?=null
     var Asar:TextView?=null
     var Maghrib:TextView?=null
     var Isha:TextView?=null
     var searchEditText:AppCompatEditText?=null
     var searchbtn: Button? = null
     val simpleDateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app2)

        fajer=findViewById(R.id.textView)
        zuhr=findViewById(R.id.textView2)
        Asar=findViewById(R.id.textView3)
        Maghrib=findViewById(R.id.textView4)
        Isha=findViewById(R.id.textView5)
        searchEditText=findViewById(R.id.Sy1)
        searchbtn = findViewById(R.id.se12)
        searchbtn?.setOnClickListener() {
            LoadPrayerTime()
        }
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

    private fun LoadPrayerTime() {

       val geocoder=Geocoder(this)
        val addressList:kotlin.collections.List<Address>?

        try {
            addressList=geocoder.getFromLocationName(searchEditText?.getText().toString(),5)
            if(addressList!=null)
            {
                val doubleLat=addressList[0].latitude
                val doubleLong=addressList[0].longitude
                val queue=Volley.newRequestQueue(this)
                val url = "https://api.aladhan.com/v1/calendar?latitude=" + doubleLat + "&longitude=" + doubleLong + ""
                val JsonObjectRequest=JsonObjectRequest(Request.Method.GET,url,null,{
                  response ->
   //                 "data": [
    //                {
    //                    "timings": {
    //                    "Fajr": "04:59 (BST)",
    //                    "Sunrise": "06:36 (BST)",
    //                    "Dhuhr": "13:04 (BST)",
    //                    "Asr": "16:36 (BST)",
    //                    "Sunset": "19:34 (BST)",
    //                    "Maghrib": "19:34 (BST)",
    //                    "Isha": "21:10 (BST)",
    //                    "Imsak": "04:49 (BST)",
    //                    "Midnight": "01:05 (BST)",
    //                    "Firstthird": "23:15 (BST)",
    //                    "Lastthird": "02:55 (BST)"

                        val JsonData:JSONArray=response.getJSONArray("data")
                           val timings = JsonData.getJSONObject(0)
                           val tim = timings.getJSONObject("timings")
                           fajer?.setText((tim.getString("Fajr")))
                           zuhr?.setText((tim.getString("Dhuhr")))
                           Asar?.setText((tim.getString("Asr")))
                           Maghrib?.setText((tim.getString("Maghrib")))
                           Isha?.setText((tim.getString("Isha")))
                }) { error ->
                    Log.d("Error", error.message!!)
                }
                queue.add(JsonObjectRequest)
            }
        }catch (Ex:Exception)
        {
         Ex.printStackTrace()
        }
    }

}