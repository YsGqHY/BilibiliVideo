package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.internal.config.MainConfig
import online.bingzi.bilibili.video.internal.config.VideoConfig
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendInfo

/**
 * Command reload
 * <p>
 * 命令·重载
 *
 * @constructor Create empty Command reload
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandReload {
    /**
     * 执行子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        execute<CommandSender> { sender, _, _ ->
            // 重载主配置文件
            MainConfig.config.reload()
            // 重载视频配置文件
            VideoConfig.config.reload()
            // 发送重载成功消息
            sender.sendInfo("reloadSuccess")
        }
    }
}
