package com.example.instant26

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class BitmapUtils {
    internal fun textToImageEncoder(text: String): Bitmap? {
        val bitMatrix: BitMatrix
        val lenght = 500
        try {
            bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, lenght, lenght, null)
        } catch (eae: IllegalArgumentException) {
            return null
        }

        val bitMatrixWidth = bitMatrix.width

        val bitMatrixHeight = bitMatrix.height

        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y)) 0x00000000.toInt() else 0xffffffff.toInt()
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.RGB_565)

        bitmap.setPixels(pixels, 0, lenght, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }
}
