package online.bingiz.bilibili.video.internal.helper

import org.bukkit.entity.Player
import taboolib.module.nms.MinecraftVersion
import taboolib.module.nms.NMSMap
import taboolib.module.nms.sendMap
import taboolib.platform.util.ItemBuilder
import java.awt.image.BufferedImage

fun Player.sendMapVersionCompatible(
    image: BufferedImage,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
) {
    when (MinecraftVersion.major) {
        MinecraftVersion.V1_20 -> {
            taboolib.module.nms.buildMap(image, hand, width, height, builder).sendTo(this)
        }

        else -> {
            this.sendMap(image, hand, width, height, builder)
        }
    }
}