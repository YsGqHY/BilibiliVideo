package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.internal.cache.baffleCache
import online.bingzi.bilibili.video.internal.cache.cookieCache
import online.bingzi.bilibili.video.internal.cache.midCache
import online.bingzi.bilibili.video.internal.config.VideoConfig
import online.bingzi.bilibili.video.internal.database.Database.Companion.setDataContainer
import online.bingzi.bilibili.video.internal.engine.NetworkEngine
import online.bingzi.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.*
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common.platform.function.submit
import taboolib.expansion.createHelper
import taboolib.module.chat.colored
import taboolib.module.lang.sendInfoMessage
import taboolib.platform.util.bukkitPlugin

@CommandHeader(
    name = "bilibili-video",
    aliases = ["bv", "bilibilivideo"],
    permission = "BilibiliVideo.command.use",
    permissionDefault = PermissionDefault.TRUE
)
object MainCommand {
    @CommandBody
    val main = mainCommand {
        createHelper()
    }

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
                getProxyPlayer(argument)?.let {
                    it.setDataContainer("mid", "")
                    midCache.invalidate(it.uniqueId)
                    cookieCache.invalidate(it.uniqueId)
                } ?: let {
                    sender.infoAsLang("PlayerNotBindMid", argument)
                    return@execute
                }
                sender.infoAsLang("PlayerUnbindSuccess", argument)
            }
        }
    }

    @CommandBody(aliases = ["open"], permission = "BilibiliVideo.command.login", permissionDefault = PermissionDefault.TRUE)
    val login = subCommand {
        // 可指定玩家启动登陆流程
        // 可选参数
        // 需要有BilibiliVideo.command.login.other权限才可使用
        dynamic(optional = true, permission = "BilibiliVideo.command.login.other") {
            suggestPlayers()
            execute<ProxyPlayer> { sender, _, argument ->
                getProxyPlayer(argument)?.let { player ->
                    if (baffleCache.hasNext(sender.name).not()) {
                        sender.infoAsLang("CommandBaffle")
                        return@execute
                    }
                    NetworkEngine.generateBilibiliQRCodeUrl(sender, player)
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

    @CommandBody(aliases = ["use"], permission = "BilibiliVideo.command.receive", permissionDefault = PermissionDefault.TRUE)
    val receive = subCommand {
        dynamic(comment = "bv") {
            suggestion<ProxyPlayer> { _, _ ->
                VideoConfig.receiveMap.keys.toList()
            }
            execute<ProxyPlayer> { sender, _, argument ->
                if (baffleCache.hasNext(sender.name).not()) {
                    sender.infoAsLang("CommandBaffle")
                    return@execute
                }
                NetworkEngine.getTripleStatusShow(sender, argument)
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
            literal("auto", optional = true) {
                execute<ProxyPlayer> { sender, context, _ ->
                    if (baffleCache.hasNext(sender.name).not()) {
                        sender.infoAsLang("CommandBaffle")
                        return@execute
                    }
                    submit(async = true) {
                        NetworkEngine.getTripleStatus(sender, context["bv"])
                    }
                }
            }
        }
    }

    @CommandBody(permission = "BilibiliVideo.command.version", permissionDefault = PermissionDefault.OP)
    val version = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            sender.sendInfoMessage("&a&l插件名称 > ${bukkitPlugin.description.name}".colored())
            sender.sendInfoMessage("&a&l插件版本 > ${bukkitPlugin.description.version}".colored())
            sender.sendInfoMessage("&a&l插件作者 > ${bukkitPlugin.description.authors.joinToString(", ")}".colored())
        }
    }
}
