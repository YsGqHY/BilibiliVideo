package online.bingzi.bilibili.video.internal.helper

import org.bukkit.Bukkit
import taboolib.common.platform.ProxyPlayer
import taboolib.module.nms.MinecraftVersion
import taboolib.module.nms.NMSMap
import taboolib.module.nms.buildMap
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
