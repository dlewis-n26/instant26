package com.example.instant26

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_qr_generator.*


class QrGeneratorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_generator)
        setSupportActionBar(toolbar)

        val intent = intent
        val message = intent.getStringExtra("data")

        val imageView: ImageView = findViewById(R.id.qr_code_image)
        val bitmap = BitmapUtils().textToImageEncoder(message)
        imageView.setImageBitmap(bitmap)

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
