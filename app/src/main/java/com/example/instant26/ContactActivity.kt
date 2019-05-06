package com.example.instant26

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val imageView = findViewById<ImageView>(R.id.contact_screen_image)

        imageView.setOnClickListener {
            val intent = Intent(this, MoneyRequestActivity::class.java)
            startActivity(intent)
        }
    }

}
