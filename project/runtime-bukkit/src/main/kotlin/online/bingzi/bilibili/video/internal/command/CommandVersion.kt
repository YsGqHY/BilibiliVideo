package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.api.BilibiliVideoNetWorkAPI
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand

/**
 * Command version
 * <p>
 * 命令·版本
 *
 * @constructor Create empty Command version
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandVersion {
    /**
     * 执行子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        // 定义一个执行命令的处理器，处理ProxyCommandSender类型的发送者
        execute<ProxyCommandSender> { sender, _, _ ->
            // 调用Bilibili视频网络API发送版本信息给发送者
            BilibiliVideoNetWorkAPI.sendVersion(sender)
        }
    }
}
