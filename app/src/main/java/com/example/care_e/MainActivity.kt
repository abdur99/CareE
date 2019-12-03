package com.example.care_e

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_trip_record)
//        setContentView(R.layout.activity_login)

        startActivity(Intent(this, LoginActivity::class.java))

//        buttonmain!!
//            .setOnClickListener { startActivity(
//                Intent(this@MainActivity, LoginActivity::class.java)
//            ) }
    }

}
