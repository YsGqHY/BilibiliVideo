package online.bingzi.bilibili.video.internal.helper

import online.bingzi.bilibili.video.internal.map.ImageMapRenderer
import org.bukkit.Bukkit
import org.bukkit.inventory.meta.MapMeta
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import java.awt.image.BufferedImage

object MapHelper {
    private val view = Bukkit.createMap(Bukkit.getWorlds()[0]).apply {
        renderers.forEach { removeRenderer(it) }
    }

    fun builderMap(image: BufferedImage, builder: ItemBuilder.() -> Unit) {
        val buildItem = buildItem(XMaterial.FILLED_MAP, builder)
        val imageMapRenderer = ImageMapRenderer(image)
        val itemMeta = buildItem.itemMeta!!
        if (itemMeta is MapMeta) {
            itemMeta
            val view = Bukkit.createMap(Bukkit.getWorlds()[0])
            view.renderers.forEach { view.removeRenderer(it) }
            view.addRenderer(ImageMapRenderer(image))
        }
    }
}