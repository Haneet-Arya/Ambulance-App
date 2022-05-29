package com.haneetarya.ambulanceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.haneetarya.ambulanceapp.databinding.ActivitySignUp3Binding

class SignUp3 : AppCompatActivity() {
    lateinit var binding: ActivitySignUp3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUp3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext, Home::class.java))
                finish()
            }

        })
    }
}