package online.bingiz.bilibili.video.internal.command

import online.bingiz.bilibili.video.internal.cache.baffleCache
import online.bingiz.bilibili.video.internal.cache.cookieCache
import online.bingiz.bilibili.video.internal.config.MainConfig
import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.*
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common.platform.function.submit
import taboolib.expansion.getDataContainer

@CommandHeader(
    name = "bilibili-video",
    aliases = ["bv", "bilibilivideo"],
    permission = "BilibiliVideo.command.use",
    permissionDefault = PermissionDefault.TRUE
)
object MainCommand {
    @CommandBody(permission = "BilibiliVideo.command.reload", permissionDefault = PermissionDefault.OP)
    val reload = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            sender.infoAsLang("CommandReloadSuccess")
        }
    }

    @CommandBody(permission = "BilibiliVideo.command.unbind", permissionDefault = PermissionDefault.OP)
    val unbind = subCommand {
        dynamic {
            suggestPlayers()
            execute<ProxyCommandSender> { sender, _, argument ->
                getProxyPlayer(argument)?.getDataContainer()?.set("mid", "") ?: let {
                    sender.infoAsLang("PlayerNotBindMid", argument)
                    return@execute
                }
                sender.infoAsLang("PlayerUnbindSuccess", argument)
            }
        }
    }

    @CommandBody(permission = "BilibiliVideo.command.login", permissionDefault = PermissionDefault.TRUE)
    val login = subCommand {
        // 可指定玩家启动登陆流程
        // 可选参数
        dynamic(optional = true, permission = "BilibiliVideo.command.login.sender") {
            suggestPlayers()
            execute<ProxyCommandSender> { _, _, argument ->
                getProxyPlayer(argument)?.let { player ->
                    if (baffleCache.hasNext(player.name).not()) {
                        player.infoAsLang("CommandBaffle")
                        return@execute
                    }
                    NetworkEngine.generateBilibiliQRCodeUrl(player)
                }
            }
        }
        execute<ProxyPlayer> { sender, _, _ ->
            if (baffleCache.hasNext(sender.name).not()) {
                sender.infoAsLang("CommandBaffle")
                return@execute
            }
            NetworkEngine.generateBilibiliQRCodeUrl(sender)
        }
    }

    @CommandBody(permission = "BilibiliVideo.command.show", permissionDefault = PermissionDefault.TRUE)
    val show = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->
            if (baffleCache.hasNext(sender.name).not()) {
                sender.infoAsLang("CommandBaffle")
                return@execute
            }
            // 因为是网络操作并且下层未进行异步操作
            // 以防卡死主线程，故这里进行异步操作
            submit(async = true) {
                NetworkEngine.getPlayerBindUserInfo(sender)?.let {
                    sender.infoAsLang("CommandShowBindUserInfo", it.uname, it.mid)
                } ?: sender.infoAsLang("CommandShowBindUserInfoNotFound")
            }
        }
    }

    @CommandBody(permission = "BilibiliVideo.command.logout", permissionDefault = PermissionDefault.TRUE)
    val logout = subCommand {
        execute<ProxyPlayer> { sender, _, _ ->
            cookieCache.invalidate(sender.uniqueId)
            sender.infoAsLang("CommandLogoutSuccess")
        }
    }

    @CommandBody(permission = "BilibiliVideo.command.receive", permissionDefault = PermissionDefault.TRUE)
    val receive = subCommand {
        dynamic(comment = "bv") {
            suggestion<ProxyPlayer> { _, _ ->
                MainConfig.receiveMap.keys.toList()
            }
            execute<ProxyPlayer> { sender, _, argument ->
                if (baffleCache.hasNext(sender.name).not()) {
                    sender.infoAsLang("CommandBaffle")
                    return@execute
                }
                NetworkEngine.getTripleStatus(sender, argument)
            }
            literal("show", optional = true) {
                execute<ProxyPlayer> { sender, context, _ ->
                    if (baffleCache.hasNext(sender.name).not()) {
                        sender.infoAsLang("CommandBaffle")
                        return@execute
                    }
                    submit(async = true) {
                        NetworkEngine.getTripleStatusShow(sender, context["bv"])
                    }
                }
            }
        }
    }
}