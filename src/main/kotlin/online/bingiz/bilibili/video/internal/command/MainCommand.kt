package online.bingiz.bilibili.video.internal.command

import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.util.infoAsLang
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.*

@CommandHeader(
    name = "bilibili-video",
    aliases = ["bv", "bilibilivideo"],
    permission = "BilibiliVideo.command.use",
    permissionDefault = PermissionDefault.TRUE
)
object MainCommand {
    @CommandBody(permission = "BilibiliVideo.command.reload", permissionDefault = PermissionDefault.OP)
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            sender.infoAsLang("CommandReloadSuccess")
        }
    }

    @CommandBody(permission = "BilibiliVideo.command.login", permissionDefault = PermissionDefault.TRUE)
    val login = subCommand {
        // 可指定玩家启动登陆流程
        // 可选参数
        dynamic(optional = true) {
            suggestPlayers()
            execute<CommandSender> { _, _, argument ->
                Bukkit.getPlayerExact(argument)?.let { player ->
                    NetworkEngine.generateBilibiliQRCodeUrl(player)
                }
            }
        }
        execute<Player> { sender, _, _ ->
            NetworkEngine.generateBilibiliQRCodeUrl(sender)
        }
    }
}