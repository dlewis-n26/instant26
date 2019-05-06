package com.example.instant26

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class ScrollingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        val requestMoneyButton = findViewById<Button>(R.id.home_request_money)
        if (ContextCompat.checkSelfPermission(
                this@ScrollingActivity,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@ScrollingActivity,
                arrayOf(Manifest.permission.CAMERA),
                100
            )
        }
        requestMoneyButton.setOnClickListener {
            val intent = Intent(this, MoneyRequestActivity::class.java)
            startActivity(intent)
        }
//        scan.setOnClickListener { view ->
//            val intent = Intent(this, ScanQrCodeActivity::class.java).apply {
//            }
//            startActivity(intent)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
