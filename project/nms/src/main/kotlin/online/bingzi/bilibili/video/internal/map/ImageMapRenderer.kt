package online.bingzi.bilibili.video.internal.map

import org.bukkit.entity.Player
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import java.awt.image.BufferedImage

class ImageMapRenderer(val image: BufferedImage) : MapRenderer() {
    private var renderer = false
    override fun render(mapView: MapView, mapCanvas: MapCanvas, player: Player) {
        if (renderer) {
            return
        }
        mapCanvas.drawImage(0, 0, image)
        renderer = true
    }
}