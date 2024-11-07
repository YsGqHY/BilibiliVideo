package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.api.BilibiliVideoNetWorkAPI
import online.bingzi.bilibili.video.internal.cache.Cache
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.submitAsync
import taboolib.module.lang.sendInfo

/**
 * Command Buvid3
 * <p>
 * 命令·Buvid3
 *
 * @constructor Create empty Command version
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandBuvid3 {
    /**
     * 执行子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        literal("show") {
            execute<ProxyCommandSender> { sender, _, _ ->
                sender.sendInfo("commandBuvid3Show", Cache.buvid3)
            }
        }
        literal("refresh") {
            execute<ProxyCommandSender> { sender, _, _ ->
                submitAsync {
                    val old = Cache.buvid3
                    BilibiliVideoNetWorkAPI.requestBilibiliGetBuvid3WriteCache()
                    val new = Cache.buvid3
                    sender.sendInfo("commandBuvid3Refresh", old, new)
                }
            }
        }
    }
}
