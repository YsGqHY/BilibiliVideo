package online.bingzi.bilibili.video.internal.nms

import org.bukkit.inventory.ItemStack
import taboolib.common.util.unsafeLazy
import taboolib.module.nms.nmsProxy
import java.awt.image.BufferedImage

abstract class NMS {
    abstract fun builderMap(image: BufferedImage): ItemStack

    companion object {
        val INSTANCE by unsafeLazy {
            nmsProxy<NMS>()
        }
    }
}