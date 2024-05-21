package online.bingzi.bilibili.video.internal.helper

import online.bingzi.bilibili.video.internal.config.SettingConfig
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import taboolib.common.platform.ProxyPlayer
import taboolib.common.util.unsafeLazy
import taboolib.library.reflex.Reflex.Companion.invokeMethod
import taboolib.library.xseries.XMaterial
import taboolib.module.nms.MinecraftVersion
import taboolib.module.nms.NMSMap
import taboolib.module.nms.buildMap
import taboolib.module.nms.sendMap
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import taboolib.platform.util.isNotAir
import taboolib.type.BukkitEquipment
import java.awt.image.BufferedImage

fun ProxyPlayer.sendMap(image: BufferedImage, builder: ItemBuilder.() -> Unit = {}) {
    if (SettingConfig.virtualization) {
        this.sendMapVersionCompatible(image, builder = builder)
    } else {
        this.castSafely<Player>()?.buildMapItem(image, builder = builder)
    }
}

private fun ProxyPlayer.sendMapVersionCompatible(
    image: BufferedImage,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
) {
    Bukkit.getPlayer(this.uniqueId)?.let {
        when (MinecraftVersion.major) {
            in MinecraftVersion.V1_18..MinecraftVersion.V1_20 -> {
                buildMap(image, hand, width, height, builder).sendTo(it)
            }

            in MinecraftVersion.V1_8..MinecraftVersion.V1_17 -> {
                it.sendMap(image, hand, width, height, builder)
            }

            else -> {
                it.sendMap(image, hand, width, height, builder)
            }
        }
    }
}

private fun Player.buildMapItem(image: BufferedImage, builder: ItemBuilder.() -> Unit = {}) {
    val handItem = BukkitEquipment.HAND.getItem(this)
    if (handItem.isNotAir()) {
        this.sendMessage("请清空主手物品")
        return
    }
    val mapRenderer = object : MapRenderer() {

        var rendered = false

        override fun render(mapView: MapView, mapCanvas: MapCanvas, player: Player) {
            if (rendered) {
                return
            }
            mapCanvas.drawImage(0, 0, image)
            rendered = true
        }
    }
    val mapView by unsafeLazy {
        val mapView = Bukkit.createMap(Bukkit.getWorlds()[0])
        mapView.addRenderer(mapRenderer)
        mapView
    }
    val mapItem = buildItem(XMaterial.FILLED_MAP) {
        damage = mapView.invokeMethod<Short>("getId")!!.toInt()
        builder(this)
    }
    BukkitEquipment.HAND.setItem(player, mapItem)
}
