package com.example.instant26

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
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
        val paymentId = "9564d3e5-77dd-4bb4-a43b-93e8f9ad821a"

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

        val updatedBalanceView = findViewById<TextView>(R.id.updated_balance_by_payer)
        for (i in 1..10) {
            HttpClient().get("https://cryptic-ravine-63583.herokuapp.com/payments/poll/$paymentId",
                object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        val string = response.body()?.string()
                        if (string != null && string != "") {
                            updatedBalanceView.text = string
                        }
                    }

                    override fun onFailure(call: Call, e: IOException) {

                    }


                })

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
