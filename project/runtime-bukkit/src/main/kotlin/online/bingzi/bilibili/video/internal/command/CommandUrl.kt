package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.api.BilibiliVideoNMSAPI
import online.bingzi.bilibili.video.internal.config.MainConfig
import online.bingzi.bilibili.video.internal.helper.ImageHelper
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.common.platform.function.getProxyPlayer
import taboolib.module.lang.sendWarn


/**
 * Command Url
 * <p>
 * 命令·传送门
 *
 * @constructor Create empty Command receive
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandUrl {
    /**
     * 执行子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        dynamic(comment = "bv") {
            execute<ProxyPlayer> { sender, context, _ ->
                // 从上下文中获取视频标识符 bv
                val bv = context["bv"]
                // 将视频 URL 转换为缓冲图像
                val bufferImage = ImageHelper.stringToBufferImage("https://www.bilibili.com/video/$bv")
                // 根据配置决定是异步还是同步发送虚拟地图
                if (MainConfig.settingAsyncSendPacket) {
                    BilibiliVideoNMSAPI.sendVirtualMapToPlayerAsync(sender.castSafely<Player>()!!, bufferImage, MainConfig.settingHand)
                } else {
                    BilibiliVideoNMSAPI.sendVirtualMapToPlayer(sender.castSafely<Player>()!!, bufferImage, MainConfig.settingHand)
                }
            }
            dynamic(comment = "player") {
                // 提供玩家建议
                suggestPlayers()
                execute<ProxyCommandSender> { sender, context, _ ->
                    // 从上下文中获取视频标识符 bv 和玩家名称
                    val bv = context["bv"]
                    val playerName = context["player"]
                    // 根据玩家名称获取玩家对象
                    val player = getProxyPlayer(playerName) ?: let {
                        // 如果未找到玩家，发送警告消息
                        sender.sendWarn("playerNotFound", playerName)
                        return@execute
                    }
                    // 将视频 URL 转换为缓冲图像
                    val bufferImage = ImageHelper.stringToBufferImage("https://www.bilibili.com/video/$bv")
                    // 根据配置决定是异步还是同步发送虚拟地图
                    if (MainConfig.settingAsyncSendPacket) {
                        BilibiliVideoNMSAPI.sendVirtualMapToPlayerAsync(player.castSafely<Player>()!!, bufferImage, MainConfig.settingHand)
                    } else {
                        BilibiliVideoNMSAPI.sendVirtualMapToPlayer(player.castSafely<Player>()!!, bufferImage, MainConfig.settingHand)
                    }
                }
            }
        }
    }
}
