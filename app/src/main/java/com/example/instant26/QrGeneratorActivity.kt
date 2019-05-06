package com.example.instant26

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_qr_generator.*

class QrGeneratorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_generator)
        setSupportActionBar(toolbar)

        val imageView: ImageView = findViewById(R.id.imageView)
        val bitmap = BitmapUtils().textToImageEncoder("gopinath")
        imageView.setImageBitmap(bitmap)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
