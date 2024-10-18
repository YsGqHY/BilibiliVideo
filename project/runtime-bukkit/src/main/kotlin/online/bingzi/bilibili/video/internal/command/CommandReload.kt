package online.bingzi.bilibili.video.internal.command

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
            // 安慰剂
            sender.sendInfo("reloadSuccess")
        }
    }
}
