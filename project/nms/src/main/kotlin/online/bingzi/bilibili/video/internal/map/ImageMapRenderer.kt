package online.bingzi.bilibili.video.internal.map

import org.bukkit.entity.Player
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import java.awt.image.BufferedImage
import kotlin.math.sqrt

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

        // 在地图画布上绘制图像
        mapCanvas.drawImage(0, 0, image)
    }

    /**
     * 获取玩家的地图缓冲区数据
     *
     * @param player 玩家对象
     * @return 地图数据缓冲区
     */
    fun getBufferForPlayer(): ByteArray {
        return buffer
    }
    
    /**
     * 将 RGB 颜色转换为 Minecraft 地图颜色索引
     * 基于 Minecraft 1.12.2 的地图调色板
     */
    private fun convertToMapColor(red: Int, green: Int, blue: Int): Byte {
        // Minecraft 1.12.2 的基础颜色数组
        val colors = arrayOf(
            // 基础颜色 (multiplier = 180)
            intArrayOf(0, 0, 0),      // 0 - 黑色
            intArrayOf(0, 0, 180),    // 1 - 深蓝色
            intArrayOf(0, 180, 0),    // 2 - 深绿色
            intArrayOf(0, 180, 180),  // 3 - 深青色
            intArrayOf(180, 0, 0),    // 4 - 深红色
            intArrayOf(180, 0, 180),  // 5 - 深紫色
            intArrayOf(180, 180, 0),  // 6 - 金色
            intArrayOf(180, 180, 180),// 7 - 灰色
            intArrayOf(90, 90, 90),   // 8 - 深灰色
            intArrayOf(90, 90, 255),  // 9 - 蓝色
            intArrayOf(90, 255, 90),  // 10 - 绿色
            intArrayOf(90, 255, 255), // 11 - 青色
            intArrayOf(255, 90, 90),  // 12 - 红色
            intArrayOf(255, 90, 255), // 13 - 粉色
            intArrayOf(255, 255, 90), // 14 - 黄色
            intArrayOf(255, 255, 255) // 15 - 白色
        )

        var closestColor = 0
        var minDistance = Double.MAX_VALUE

        // 计算输入颜色与每个基础颜色的距离，找到最接近的颜色
        for (i in colors.indices) {
            val dr = red - colors[i][0]
            val dg = green - colors[i][1]
            val db = blue - colors[i][2]
            
            // 使用加权欧几里得距离，考虑人眼对绿色的敏感度更高
            val distance = sqrt(
                (dr * dr * 0.299) + 
                (dg * dg * 0.587) + 
                (db * db * 0.114)
            )

            if (distance < minDistance) {
                minDistance = distance
                closestColor = i
            }
        }

        // 根据亮度调整色阶
        val brightness = (red * 0.299 + green * 0.587 + blue * 0.114).toInt()
        val shade = when {
            brightness > 200 -> 3  // 最亮
            brightness > 150 -> 2  // 较亮
            brightness > 100 -> 1  // 较暗
            else -> 0             // 最暗
        }

        // 计算最终的颜色索引
        return ((closestColor * 4) + shade).toByte()
    }
}
