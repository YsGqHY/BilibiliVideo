package online.bingzi.bilibili.video.internal.map

import org.bukkit.entity.Player
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import java.awt.image.BufferedImage

/**
 * Image map renderer
 * <p>
 * 用于渲染图像到地图上
 *
 * @param image 要渲染的图像
 * @constructor Create empty Image map renderer
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class ImageMapRenderer(private val image: BufferedImage) : MapRenderer(false) {
    // 缓存每个玩家的渲染状态
    private val renderedPlayers = mutableSetOf<Player>()
    
    // 初始化缓冲区，假设地图大小为128x128
    private var buffer: ByteArray = ByteArray(128 * 128)

    init {
        // 预处理图像以适应地图大小
        val scaledImage = if (image.width != 128 || image.height != 128) {
            val scaled = BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB)
            val g = scaled.createGraphics()
            g.drawImage(image, 0, 0, 128, 128, null)
            g.dispose()
            scaled
        } else {
            image
        }
        
        // 预先计算颜色数据
        for (x in 0 until 128) {
            for (y in 0 until 128) {
                val rgb = scaledImage.getRGB(x, y)
                val alpha = (rgb shr 24) and 0xFF
                if (alpha < 128) {
                    // 透明像素使用 0
                    buffer[x + y * 128] = 0
                } else {
                    val red = (rgb shr 16) and 0xFF
                    val green = (rgb shr 8) and 0xFF
                    val blue = rgb and 0xFF
                    // 转换为 Minecraft 地图颜色索引
                    buffer[x + y * 128] = convertToMapColor(red, green, blue)
                }
            }
        }
    }

    /**
     * Render
     * <p>
     * 渲染地图视图。
     *
     * @param mapView 地图视图对象
     * @param mapCanvas 地图画布对象
     * @param player 执行渲染的玩家对象
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    override fun render(mapView: MapView, mapCanvas: MapCanvas, player: Player) {
        if (!renderedPlayers.add(player)) {
            return
        }
        
        // 直接将预处理的数据应用到画布
        for (x in 0 until 128) {
            for (y in 0 until 128) {
                mapCanvas.setPixel(x, y, buffer[x + y * 128])
            }
        }
    }

    /**
     * 获取玩家的地图缓冲区数据
     *
     * @param player 玩家对象
     * @return 地图数据缓冲区
     */
    fun getBufferForPlayer(player: Player): ByteArray {
        return buffer
    }
    
    /**
     * 将 RGB 颜色转换为 Minecraft 地图颜色索引
     */
    private fun convertToMapColor(red: Int, green: Int, blue: Int): Byte {
        // 简化的颜色映射算法
        val index = when {
            red < 64 && green < 64 && blue < 64 -> 0 // 黑色
            red > 191 && green > 191 && blue > 191 -> 34 // 白色
            red > 191 && green < 64 && blue < 64 -> 18 // 红色
            red < 64 && green > 191 && blue < 64 -> 30 // 绿色
            red < 64 && green < 64 && blue > 191 -> 50 // 蓝色
            else -> ((red + green + blue) / 3 * 4 / 255).toByte() // 灰度
        }
        return index.toByte()
    }
}
