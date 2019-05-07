package com.example.instant26

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.instant26.http.HttpClient
import com.example.instant26.model.PaymentDto
import com.example.instant26.model.Transaction
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class PayConfirmationActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_confirmation)

        val message = intent.getStringExtra("data")

        val value = ObjectMapper().readValue(message, PaymentDto::class.java)

        val iban = findViewById<TextView>(R.id.payment_iban_text_view)
        val requesterName = findViewById<TextView>(R.id.payment_requester_name_text_view)
        val amount = findViewById<TextView>(R.id.payment_amount_text_view)
        val button = findViewById<Button>(R.id.payment_confirm_button)

        requesterName.text = "Requester: ${value.requestor}"
        iban.text = "IBAN: ${value.iban}"
        amount.text = "Amount: ${value.amount.toString()} EUR"

        button.setOnClickListener {
            val paymentDto = ObjectMapper().readValue(message, PaymentDto::class.java)
            val t = Transaction("Lukas", false)
            val tString = ObjectMapper().writeValueAsString(t)
            HttpClient().post("https://cryptic-ravine-63583.herokuapp.com/payments/${paymentDto.id}/transactions",
                tString,
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("qr-scan", "failed", e)

                    }

                    override fun onResponse(call: Call, response: Response) {
                        Log.v("qr-scan", "succ")
                        showConfirmDialog(value.requestor)
                    }

                }
            )
        }


    }

    fun showConfirmDialog(to: String) {
        runOnUiThread {
            val alertDialog = AlertDialog.Builder(this@PayConfirmationActivity).create()
            alertDialog.setTitle("Payment Confirmation")
            alertDialog.setMessage("Money sent to $to")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ ->
                dialog.dismiss()

                val intent = Intent(this, ScrollingActivity::class.java)
                intent.putExtra("new-amount", "70.0")
                startActivity(intent)
            }
            alertDialog.show()
        }
    }

}
