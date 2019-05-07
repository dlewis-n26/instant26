package com.example.instant26

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.instant26.http.HttpClient
import com.example.instant26.model.PaymentDto
import com.fasterxml.jackson.databind.ObjectMapper
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

        val value = ObjectMapper().readValue(message, PaymentDto::class.java)

        val imageView: ImageView = findViewById(R.id.qr_code_image)
        val bitmap = BitmapUtils().textToImageEncoder(message)
        imageView.setImageBitmap(bitmap)
        val paymentId = value.id

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
        var shouldPoll = 1

        var finalText = ""
        val handler = Handler()
        handler.postDelayed(
            Runnable {
                while (shouldPoll == 1) {
                    HttpClient().get("https://cryptic-ravine-63583.herokuapp.com/payments/poll/$paymentId",
                        object : Callback {
                            override fun onResponse(call: Call, response: Response) {
                                val string = response.body()?.string()
                                if (string != null && string != "") {
                                    finalText = string
                                    shouldPoll = 2
                                    Log.v("qr", "succ-response")
                                } else {
                                    Log.v("qr", "no-response")
                                }
                            }

                            override fun onFailure(call: Call, e: IOException) {
                                shouldPoll = 3
                                Log.e("qr", "fail-response", e)
                            }

                        })
                }
            },
            3000
        )


        if (shouldPoll == 2) {
            updatedBalanceView.text = finalText
            Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
        } else if (shouldPoll == 3) {
            Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
