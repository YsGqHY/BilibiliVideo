package online.bingzi.bilibili.video.internal.command

import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendInfo

object CommandReload {
    var execute = subCommand {
        execute<CommandSender> { sender, _, _ ->
            // 安慰剂
            sender.sendInfo("reloadSuccess")
        }
    }
}
