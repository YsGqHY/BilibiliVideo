package online.bingzi.bilibili.video.internal.nms

import online.bingzi.bilibili.video.internal.entity.HandEnum
import org.bukkit.entity.Player
import taboolib.common.util.unsafeLazy
import taboolib.module.nms.nmsProxy
import taboolib.platform.util.ItemBuilder
import java.awt.image.BufferedImage

/**
 * Nms
 * <p>
 * NMS类是一个抽象类，定义了与玩家和图像交互的相关方法。
 *
 * @constructor Create empty Nms
 *
 * @author BingZi-233
 * @since 2.0.0
 */
abstract class NMS {
    /**
     * Send virtual map to player
     * <p>
     * 发送虚拟地图给指定的玩家。
     *
     * @param player 目标玩家对象
     * @param bufferedImage 要发送的图像
     * @param hand 玩家手中的物品类型
     * @param itemBuilder 用于构建物品的lambda表达式
     * @receiver [ItemBuilder]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    abstract fun sendVirtualMapToPlayer(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit)

    /**
     * Send virtual map to player async
     * <p>
     * 发送虚拟地图给指定的玩家。
     *
     * @param player 目标玩家对象
     * @param bufferedImage 要发送的图像
     * @param hand 玩家手中的物品类型
     * @param itemBuilder 用于构建物品的lambda表达式
     * @receiver [ItemBuilder]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    abstract fun sendVirtualMapToPlayerAsync(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit)

    companion object {
        /**
         * 获取NMS类的单例实例。
         *
         * @author BingZi-233
         * @since 2.0.0
         */
        val INSTANCE by unsafeLazy {
            nmsProxy<NMS>()
        }
    }
}

