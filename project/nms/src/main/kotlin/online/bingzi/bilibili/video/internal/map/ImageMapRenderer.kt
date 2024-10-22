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
class ImageMapRenderer(val image: BufferedImage) : MapRenderer() {
    // 标记是否已经渲染过
    private var renderer = false

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
        // 如果已经渲染过，直接返回
        if (renderer) {
            return
        }
        // 在地图画布上绘制图像
        mapCanvas.drawImage(0, 0, image)
        // 设置为已渲染状态
        renderer = true
    }
}
