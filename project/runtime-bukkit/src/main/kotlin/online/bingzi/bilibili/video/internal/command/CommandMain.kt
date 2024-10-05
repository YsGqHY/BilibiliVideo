package online.bingzi.bilibili.video.internal.command

import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

@CommandHeader(
    name = "BilibiliVideo",
    aliases = ["bv", "bilibili-video"],
    permission = "BilibiliVideo.command.use",
    permissionDefault = PermissionDefault.TRUE
)
object CommandMain {
    @CommandBody
    val main = mainCommand { createHelper(checkPermissions = true) }

    @CommandBody(aliases = ["open"], permission = "BilibiliVideo.command.login", permissionDefault = PermissionDefault.TRUE)
    val login = CommandLogin.execute

    @CommandBody(permission = "BilibiliVideo.command.logout", permissionDefault = PermissionDefault.TRUE)
    val logout = CommandLogout.execute

    @CommandBody(aliases = ["use"], permission = "BilibiliVideo.command.receive", permissionDefault = PermissionDefault.TRUE)
    val receive = CommandReceive.execute

    @CommandBody(permission = "BilibiliVideo.command.reload", permissionDefault = PermissionDefault.OP)
    val reload = CommandReload.execute

    @CommandBody(permission = "BilibiliVideo.command.unbind", permissionDefault = PermissionDefault.OP)
    val unbind = CommandUnbind.execute

    @CommandBody(permission = "BilibiliVideo.command.url", permissionDefault = PermissionDefault.TRUE)
    val url = CommandUrl.execute

    @CommandBody(permission = "BilibiliVideo.command.version", permissionDefault = PermissionDefault.OP)
    val version = CommandVersion.execute
}
