package com.example.messsger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        regiter_buttom.setOnClickListener {
            val email = email_edittext.text.toString()
            val password = password_edittext.text.toString()

            Log.d("MainActivity","Email  is "+email)
            Log.d("MainActivity","Password :$password ") }

        aleady_have_acount_textView.setOnClickListener {
            Log.d("MainActivity","Try to show login activity")

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}