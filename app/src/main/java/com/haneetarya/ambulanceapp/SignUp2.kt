package com.haneetarya.ambulanceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.haneetarya.ambulanceapp.databinding.ActivitySignUp2Binding

class SignUp2 : AppCompatActivity() {
    lateinit var binding: ActivitySignUp2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignUp2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val email=intent.getStringExtra("email")

        binding.nextBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(binding.phoneEditText.text.toString()=="" || binding.passwordEdittext.text.toString()==""){
                    Toast.makeText(applicationContext,"Please Enter your Phone and Password",
                        Toast.LENGTH_LONG).show()
                }
                else{
                    val intent = Intent(applicationContext,SignUp3::class.java)

                    startActivity(intent)
                }
            }

        })


    }
}