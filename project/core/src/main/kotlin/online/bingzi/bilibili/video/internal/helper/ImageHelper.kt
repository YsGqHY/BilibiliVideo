package online.bingzi.bilibili.video.internal.helper

import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import taboolib.common.env.RuntimeDependency
import java.awt.Color
import java.awt.image.BufferedImage

/**
 * Image helper
 * <p>
 * 图像辅助类
 *
 * @constructor Create empty Image helper
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@RuntimeDependency(value = "!com.google.zxing:core:3.5.2", relocate = ["!com.google.zxing", "!online.bingzi.bilibili.video.library.zxing"])
object ImageHelper {
    /**
     * String to buffer image
     * <p>
     * 字符串转图像
     *
     * @param content 内容
     * @param size 默认 128
     * @return [BufferedImage]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun stringToBufferImage(content: String, size: Int = 128): BufferedImage {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)
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
}