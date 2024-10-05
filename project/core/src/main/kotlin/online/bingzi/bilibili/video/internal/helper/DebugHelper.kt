package online.bingzi.bilibili.video.internal.helper

import online.bingzi.bilibili.video.internal.config.MainConfig
import taboolib.common.platform.function.console
import taboolib.module.lang.sendInfoMessage

/**
 * Debug
 * <p>
 * 调试日志
 *
 * @param message
 *
 * @author BingZi-233
 * @since 2.0.0
 */
fun debug(message: String) {
    if (MainConfig.debugStatus) {
        console().sendInfoMessage("[Debug] > $message")
    }
}
