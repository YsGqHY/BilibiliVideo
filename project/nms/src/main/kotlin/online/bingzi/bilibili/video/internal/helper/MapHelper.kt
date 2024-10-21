package online.bingzi.bilibili.video.internal.helper

import online.bingzi.bilibili.video.internal.map.ImageMapRenderer
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import java.awt.image.BufferedImage

/**
 * Map helper
 * <p>
 * 地图构建器
 *
 * @constructor Create empty Map helper
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object MapHelper {
    /**
     * Builder map
     * <p>
     * 构建地图
     *
     * @param image 图像
     * @param builder 物品构建
     * @receiver [ItemBuilder]
     */
    fun builderMap(image: BufferedImage, builder: ItemBuilder.() -> Unit): ItemStack {
        val buildItem = buildItem(XMaterial.FILLED_MAP, builder)
        val imageMapRenderer = ImageMapRenderer(image)
        val mapView = Bukkit.createMap(Bukkit.getWorlds()[0]).apply {
            renderers.forEach { removeRenderer(it) }
        }
        mapView.addRenderer(imageMapRenderer)
        val itemMeta = buildItem.itemMeta!!
        if (itemMeta is MapMeta) {
            val view = Bukkit.createMap(Bukkit.getWorlds()[0])
            view.renderers.forEach { view.removeRenderer(it) }
            view.addRenderer(ImageMapRenderer(image))
            itemMeta.mapView = view
        }
        buildItem.itemMeta = itemMeta
        return buildItem
    }
}