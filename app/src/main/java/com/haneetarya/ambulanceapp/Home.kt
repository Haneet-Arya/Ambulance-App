package com.haneetarya.ambulanceapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.haneetarya.ambulanceapp.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences("ambulance", MODE_PRIVATE)
        val editor = sp.edit()
        if(sp.getString("already","").equals("")){
            editor.putString("already","yes")
            editor.apply()
        }
        binding.bookbtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,NearestHospital::class.java))
            }

        })
    }
}