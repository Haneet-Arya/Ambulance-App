package com.haneetarya.ambulanceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.haneetarya.ambulanceapp.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener{
            if(binding.emailorph.text.toString().equals("")||binding.password.text.equals("")){
                Toast.makeText(applicationContext,"Enter the details",Toast.LENGTH_LONG).show()
            }
            else{
                startActivity(Intent(applicationContext,Home::class.java))
            }
        }
    }
}