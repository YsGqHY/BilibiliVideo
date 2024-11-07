package online.bingzi.bilibili.video.api

import online.bingzi.bilibili.video.internal.entity.HandEnum
import online.bingzi.bilibili.video.internal.nms.NMS
import org.bukkit.entity.Player
import taboolib.platform.util.ItemBuilder
import java.awt.image.BufferedImage

/**
 * Bilibili video NMS API
 * <p>
 * Bilibili Video NMS API
 *
 * @constructor Create empty Bilibili video NMS API
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object BilibiliVideoNMSAPI {
    /**
     * Send virtual map to player
     * <p>
     * 发送虚拟地图给玩家
     *
     * @param player 目标玩家
     * @param bufferedImage 图片
     * @param hand 手
     * @param itemBuilder 构建器
     * @receiver [ItemBuilder]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun sendVirtualMapToPlayer(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit) {
        NMS.INSTANCE.sendVirtualMapToPlayer(player, bufferedImage, hand, itemBuilder)
    }

    /**
     * Send virtual map to player
     * <p>
     * 发送虚拟地图给玩家
     *
     * @param player 目标玩家
     * @param bufferedImage 图片
     * @param hand 手
     * @receiver [ItemBuilder]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun sendVirtualMapToPlayer(player: Player, bufferedImage: BufferedImage, hand: HandEnum) {
        NMS.INSTANCE.sendVirtualMapToPlayer(player, bufferedImage, hand) {

        }
    }

    /**
     * Send virtual map to player async
     * <p>
     * 异步发送虚拟地图给玩家
     *
     * @param player 目标玩家
     * @param bufferedImage 图片
     * @param hand 手
     * @param itemBuilder 构建器
     * @receiver [ItemBuilder]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun sendVirtualMapToPlayerAsync(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit) {
        NMS.INSTANCE.sendVirtualMapToPlayerAsync(player, bufferedImage, hand, itemBuilder)
    }


    /**
     * Send virtual map to player async
     * <p>
     * 异步发送虚拟地图给玩家
     *
     * @param player 目标玩家
     * @param bufferedImage 图片
     * @param hand 手
     * @receiver [ItemBuilder]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun sendVirtualMapToPlayerAsync(player: Player, bufferedImage: BufferedImage, hand: HandEnum) {
        NMS.INSTANCE.sendVirtualMapToPlayerAsync(player, bufferedImage, hand) {

        }
    }
}
