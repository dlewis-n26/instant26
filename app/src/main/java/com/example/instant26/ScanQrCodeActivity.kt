package com.example.instant26


import android.content.Intent
import android.graphics.PointF
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.dlazaro66.qrcodereaderview.QRCodeReaderView

class ScanQrCodeActivity : AppCompatActivity(), QRCodeReaderView.OnQRCodeReadListener {
    var sent = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr_code)


        val qrCodeReaderView: QRCodeReaderView = findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.setAutofocusInterval(2000L)

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        if (sent != 2) {
            val makeText = Toast.makeText(this, ".", Toast.LENGTH_SHORT)
            makeText.show()
            moveToConfirmationScreen(text)
        }
    }

    fun moveToConfirmationScreen(data: String?) {
        runOnUiThread {
            val intent = Intent(this, PayConfirmationActivity::class.java)
            intent.putExtra("data", data)
            startActivity(intent)
        }
    }

}
