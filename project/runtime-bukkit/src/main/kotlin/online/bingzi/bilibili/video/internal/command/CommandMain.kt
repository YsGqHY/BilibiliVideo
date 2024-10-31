package online.bingzi.bilibili.video.internal.command

import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

/**
 * Command main
 * <p>
 * 主命令
 *
 * @constructor Create empty Command main
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@CommandHeader(
    name = "BilibiliVideo", aliases = ["bv", "bilibili-video"], permission = "BilibiliVideo.command.use", permissionDefault = PermissionDefault.TRUE
)
object CommandMain {
    /**
     * Main
     * <p>
     * 定义主命令，创建命令帮助器并检查权限
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @CommandBody
    val main = mainCommand { createHelper(checkPermissions = true) }

    /**
     * Login
     * <p>
     * 定义登录命令，设置别名和权限
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @CommandBody(aliases = ["open"], permission = "BilibiliVideo.command.login", permissionDefault = PermissionDefault.TRUE)
    val login = CommandLogin.execute

    /**
     * Logout
     * <p>
     * 定义登出命令，设置权限
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @CommandBody(aliases = ["quit"], permission = "BilibiliVideo.command.logout", permissionDefault = PermissionDefault.TRUE)
    val logout = CommandLogout.execute

    /**
     * Receive
     * <p>
     * 定义接收命令，设置别名和权限
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @CommandBody(aliases = ["use"], permission = "BilibiliVideo.command.receive", permissionDefault = PermissionDefault.TRUE)
    val receive = CommandReceive.execute

    /**
     * Reload
     * <p>
     * 定义重载命令，设置权限
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @CommandBody(permission = "BilibiliVideo.command.reload", permissionDefault = PermissionDefault.OP)
    val reload = CommandReload.execute

    /**
     * Unbind
     * <p>
     * 定义解绑命令，设置权限
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @CommandBody(permission = "BilibiliVideo.command.unbind", permissionDefault = PermissionDefault.OP)
    val unbind = CommandUnbind.execute

    /**
     * Url
     * <p>
     * 定义URL命令，设置权限
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @CommandBody(aliases = ["video"], permission = "BilibiliVideo.command.url", permissionDefault = PermissionDefault.TRUE)
    val url = CommandUrl.execute

    /**
     * Version
     * <p>
     * 定义版本命令，设置权限
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @CommandBody(aliases = ["v"], permission = "BilibiliVideo.command.version", permissionDefault = PermissionDefault.OP)
    val version = CommandVersion.execute
}

