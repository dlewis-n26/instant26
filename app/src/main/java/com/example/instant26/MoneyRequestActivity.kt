package com.example.instant26

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instant26.model.PaymentDto
import com.fasterxml.jackson.databind.ObjectMapper

class MoneyRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_request)

        val requestMessage = findViewById<EditText>(R.id.request_message_edit_text).text
        val amount = findViewById<TextView>(R.id.request_amount_edit_text).text
        val requestButton = findViewById<Button>(R.id.generate_qr_code_button)
        val paymentId = "14ad9fe9-0526-40e3-b7e1-e9d5e1cd8638"

        requestButton.setOnClickListener {
            val paymentDto = PaymentDto(
                paymentId,
                "Gopinath",
                "DE",
                requestMessage.toString(),
                amount.toString().toDouble()
            )
            val json = ObjectMapper().writeValueAsString(paymentDto)

            val intent = Intent(this, QrGeneratorActivity::class.java)
            intent.putExtra("data", json)
            startActivity(intent)
        }

    }

    fun requestJson(paymentId: String, name: String, iban: String, amount: Long, message: String) = """
        {
            "id": "$paymentId",
            "requestor": "$name",
            "IBAN": "$iban",
            "amount": $amount,
            "message": "$message"
        }
    """.trimIndent()

}

