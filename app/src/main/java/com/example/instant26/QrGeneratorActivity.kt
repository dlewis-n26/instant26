package com.example.instant26

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.example.instant26.http.HttpClient
import kotlinx.android.synthetic.main.activity_qr_generator.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class QrGeneratorActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_generator)
        setSupportActionBar(toolbar)

        val intent = intent
        val message = intent.getStringExtra("data")

        val imageView: ImageView = findViewById(R.id.qr_code_image)
        val bitmap = BitmapUtils().textToImageEncoder(message)
        imageView.setImageBitmap(bitmap)


        HttpClient().post("https://cryptic-ravine-63583.herokuapp.com/payments", message,
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("qr", "failed", e)
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.v("qr", "succ")
                }

            }
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
