package com.example.messsger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button.setOnClickListener {
            val email = email_login.text.toString()
            val password = password_edittext.text.toString()

Log.d("Login","Attempt login with email/pw :$email/**")
}
back_to_registrantion_textView.setOnClickListener {
    finish()
}
}
}