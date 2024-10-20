package online.bingzi.bilibili.video.internal.nms

import online.bingzi.bilibili.video.internal.map.ImageMapRenderer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import java.awt.image.BufferedImage

class NMSImpl : NMS() {
    override fun builderMap(image: BufferedImage): ItemStack {
        val item = ItemStack(Material.FILLED_MAP)
        val view = Bukkit.createMap(Bukkit.getWorlds()[0])
        view.renderers.forEach { view.removeRenderer(it) }
        view.addRenderer(ImageMapRenderer(image))
        val itemMeta = item.itemMeta
        if (itemMeta is MapMeta) {
            itemMeta.mapView = view
            item.itemMeta = itemMeta
        }
        return item
    }
}