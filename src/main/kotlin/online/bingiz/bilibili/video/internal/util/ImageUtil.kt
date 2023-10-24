package online.bingiz.bilibili.video.internal.util

import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.Color
import java.awt.image.BufferedImage

fun String.toBufferedImage(size: Int): BufferedImage {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(this, BarcodeFormat.QR_CODE, size, size)
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.WHITE
    graphics.fillRect(0, 0, size, size)
    graphics.color = Color.BLACK
    for (x in 0 until size) {
        for (y in 0 until size) {
            if (bitMatrix.get(x, y)) {
                graphics.fillRect(x, y, 1, 1)
            }
        }
    }
    graphics.dispose()
    return image
}