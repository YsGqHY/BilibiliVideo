package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.api.BilibiliVideoAPI
import online.bingzi.bilibili.video.api.event.BilibiliPlayerQuitEvent
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.common.platform.function.getProxyPlayer
import taboolib.module.lang.sendInfo
import taboolib.module.lang.sendWarn

/**
 * Command logout
 * <p>
 * 命令·登出
 *
 * @constructor Create empty Command logout
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandLogout {
    /**
     * 执行登出子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        // 定义一个执行命令的处理器，处理ProxyPlayer类型的发送者
        execute<ProxyPlayer> { sender, _, _ ->
            // 获取并删除与发送者唯一ID相关的Bilibili视频播放器Cookie实体
            BilibiliVideoAPI.getPlayerCookieEntity(sender.uniqueId)?.delete()
            // 向发送者发送注销成功的信息
            sender.sendInfo("commandLogoutSuccess")
        }
        // 定义一个动态参数，参数名为"player"，可选，权限为"BilibiliVideo.command.logout.player"
        dynamic(comment = "player", optional = true, permission = "BilibiliVideo.command.logout.player") {
            // 提供玩家名称的建议
            suggestPlayers()
            // 定义一个执行命令的处理器，处理ProxyCommandSender类型的发送者
            execute<ProxyCommandSender> { sender, context, _ ->
                // 从上下文中获取玩家名称
                val playerName = context["player"]
                // 根据玩家名称获取ProxyPlayer对象，如果未找到则发送警告信息
                val proxyPlayer = getProxyPlayer(playerName) ?: let {
                    sender.sendWarn("playerNotFound", playerName)
                    return@execute
                }
                // 获取与该ProxyPlayer相关的Bilibili视频播放器Cookie实体
                BilibiliVideoAPI.getPlayerCookieEntity(proxyPlayer.uniqueId)?.let {
                    // 创建并调用BilibiliPlayerQuitEvent事件
                    val bilibiliPlayerQuitEvent = BilibiliPlayerQuitEvent(sender.castSafely<Player>()!!)
                    bilibiliPlayerQuitEvent.call()
                    // 如果事件被取消，则返回
                    if (bilibiliPlayerQuitEvent.isCancelled) {
                        return@execute
                    }
                    // 删除与ProxyPlayer相关的Cookie实体
                    it.delete()
                }
                // 向发送者发送注销特定玩家成功的信息
                sender.sendInfo("commandLogoutPlayerSuccess", playerName)
            }
        }
    }
}
