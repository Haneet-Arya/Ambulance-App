package com.haneetarya.ambulanceapp

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.haneetarya.ambulanceapp.databinding.ActivityGetStartedBinding

class GetStarted : AppCompatActivity() {
    lateinit var binding: ActivityGetStartedBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }
                else -> {
                    // No location access granted.

                }
            }
        }


        val sp = getSharedPreferences("ambulance", MODE_PRIVATE)
        if(!sp.getString("already","").equals("")){
            startActivity(Intent(applicationContext,Home::class.java))
            finish()
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

//        val btn = findViewById<Button>(R.id.signup_btn)
//        btn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                startActivity(Intent(applicationContext, SignUp::class.java))
//                finish()
//            }
//
//        })
        binding.signupBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext, SignUp::class.java))
                finish()
            }

        })
        binding.login.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext, Login::class.java))
                finish()
            }

        })

    }
}