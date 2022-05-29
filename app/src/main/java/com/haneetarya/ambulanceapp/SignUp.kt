package com.haneetarya.ambulanceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.haneetarya.ambulanceapp.databinding.ActivityNearestHospitalBinding
import com.haneetarya.ambulanceapp.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        
        binding.nextBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(binding.nameEditText.text.toString()=="" || binding.emailEdittext.text.toString()==""){
                    Toast.makeText(applicationContext,"Please Enter your name and email address",Toast.LENGTH_LONG).show()

                }
                else{
                    val intent = Intent(applicationContext,SignUp2::class.java)
                    intent.putExtra("name",binding.nameEditText.text.toString())
                    intent.putExtra("email",binding.emailEdittext.text.toString())
                    startActivity(intent)
                }
            }

        })
    }
}