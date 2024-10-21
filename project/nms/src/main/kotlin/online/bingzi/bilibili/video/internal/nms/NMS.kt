package online.bingzi.bilibili.video.internal.nms

import online.bingzi.bilibili.video.internal.entity.HandEnum
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.util.unsafeLazy
import taboolib.module.nms.nmsProxy

abstract class NMS {
    abstract fun sendPlayerMap(player: Player, itemStack: ItemStack, hand: HandEnum)

    companion object {
        val INSTANCE by unsafeLazy {
            nmsProxy<NMS>()
        }
    }
}