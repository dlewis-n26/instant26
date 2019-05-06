package com.example.instant26

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.content_scan_qr_code.*


class ScanQrCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr_code)
//        setSupportActionBar(toolbar)


        buttonFoo.setOnClickListener { view ->
            val myImageView = findViewById<View>(R.id.imgview) as ImageView
            val myBitmap = BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.puppy
            )
            myImageView.setImageBitmap(myBitmap)

            val detector = BarcodeDetector.Builder(applicationContext)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()
            if (!detector.isOperational) {
                txtContent.setText("Could not set up the detector!")
            }
        }
    }

}
