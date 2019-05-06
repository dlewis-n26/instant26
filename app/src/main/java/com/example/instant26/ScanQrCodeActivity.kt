package com.example.instant26

import android.graphics.PointF


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.dlazaro66.qrcodereaderview.QRCodeReaderView

class ScanQrCodeActivity : AppCompatActivity(), QRCodeReaderView.OnQRCodeReadListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr_code)


        val qrCodeReaderView: QRCodeReaderView = findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.setAutofocusInterval(2000L)

    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        val makeText = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        makeText.show()
    }

}
