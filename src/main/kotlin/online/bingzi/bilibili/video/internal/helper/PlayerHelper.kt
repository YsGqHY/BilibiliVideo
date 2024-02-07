package online.bingzi.bilibili.video.internal.helper

import org.bukkit.Bukkit
import taboolib.common.platform.ProxyPlayer
import taboolib.module.nms.MinecraftVersion
import taboolib.module.nms.NMSMap
import taboolib.module.nms.sendMap
import taboolib.platform.util.ItemBuilder
import java.awt.image.BufferedImage

fun ProxyPlayer.sendMapVersionCompatible(
    image: BufferedImage,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
) {
    this.uniqueId
    Bukkit.getPlayer(this.uniqueId)?.let {
        when (MinecraftVersion.major) {
            MinecraftVersion.V1_20 -> {
                taboolib.module.nms.buildMap(image, hand, width, height, builder).sendTo(it)
            }

            else -> {
                it.sendMap(image, hand, width, height, builder)
            }
        }
    }
}
