package com.example.instant26

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.instant26.http.HttpClient
import com.example.instant26.model.PaymentDto
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class QrGeneratorActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_generator)

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
        val handler = Handler(Looper.getMainLooper())


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
                                    updateUI(finalText)
                                    Log.v("qr", "succ-response")
                                } else {
                                    Log.v("qr", "no-response" + response.code() + response.body())
                                }
                            }

                            override fun onFailure(call: Call, e: IOException) {
                                shouldPoll = 3
                                Log.e("qr", "fail-response", e)
                            }

                        })
                    Thread.sleep(2000)
                }
            },
            5000
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun updateUI(finalText: String) {
        this.runOnUiThread {
            val alertDialog = AlertDialog.Builder(this@QrGeneratorActivity).create()
            alertDialog.setTitle("Payement Confirmation")
            alertDialog.setMessage(finalText)
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ ->
                dialog.dismiss()

                val intent = Intent(this, ScrollingActivity::class.java)
                intent.putExtra("new-amount", "130.0")
                startActivity(intent)
            }
            alertDialog.show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, ScrollingActivity::class.java)
        startActivity(intent)
    }

}
