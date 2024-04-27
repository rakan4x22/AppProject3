package com.example.appproject

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class App2 : AppCompatActivity() {

    var fajer: TextView? = null
    var zuhr: TextView? = null
    var Asar: TextView? = null
    var Maghrib: TextView? = null
    var Isha: TextView? = null
    var searchEditText: AppCompatEditText? = null
    var searchbtn: Button? = null
    val simpleDateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app2)

        fajer = findViewById(R.id.textView)
        zuhr = findViewById(R.id.textView2)
        Asar = findViewById(R.id.textView3)
        Maghrib = findViewById(R.id.textView4)
        Isha = findViewById(R.id.textView5)
        searchEditText = findViewById(R.id.Sy1)
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

        val geocoder = Geocoder(this)
        val addressList: kotlin.collections.List<Address>?

        try {
            addressList = geocoder.getFromLocationName(searchEditText?.text.toString(), 5)
            if (addressList != null) {
                val doubleLat = addressList[0].latitude
                val doubleLong = addressList[0].longitude
                val queue = Volley.newRequestQueue(this)
                val url = "https://api.aladhan.com/v1/calendar?latitude=$doubleLat&longitude=$doubleLong"
                val JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                    { response ->
                        val jsonData: JSONArray = response.getJSONArray("data")
                        val timings = jsonData.getJSONObject(0)
                        val tim = timings.getJSONObject("timings")
                        fajer?.text = tim.getString("Fajr")
                        zuhr?.text = tim.getString("Dhuhr")
                        Asar?.text = tim.getString("Asr")
                        Maghrib?.text = tim.getString("Maghrib")
                        Isha?.text = tim.getString("Isha")

                        // Create notification for each prayer time
                        createPrayerTimeNotification(tim)
                    },
                    { error ->
                        Log.d("Error", error.message!!)
                    }
                )
                queue.add(JsonObjectRequest)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun createPrayerTimeNotification(timings: JSONObject) {
        val prayerTimes = arrayOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha")
        val notificationTitles = arrayOf("Fajr Prayer", "Zuhr Prayer", "Asr Prayer", "Maghrib Prayer", "Isha Prayer")

        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)

        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        for (i in prayerTimes.indices) {
            val prayerTime = simpleDateFormat.parse(timings.getString(prayerTimes[i]))
            val prayerCalendar = Calendar.getInstance()
            prayerCalendar.time = prayerTime

            val prayerHour = prayerCalendar.get(Calendar.HOUR_OF_DAY)
            val prayerMinute = prayerCalendar.get(Calendar.MINUTE)

            if (prayerHour == currentHour && prayerMinute == currentMinute) {
                val notificationBuilder = NotificationCompat.Builder(this, "prayer_channel")
                    .setSmallIcon(R.drawable.icon13)
                    .setContentTitle(notificationTitles[i])
                    .setContentText("It's time for ${prayerTimes[i]} prayer")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                try {
                    // Check if the app has the required permission before posting the notification
                    if (checkSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                        with(NotificationManagerCompat.from(this)) {
                            notify(i, notificationBuilder.build())
                        }
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                    // Handle the exception (e.g., show an error message or request the permission)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Prayer Channel"
            val descriptionText = "Channel for prayer notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("prayer_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}