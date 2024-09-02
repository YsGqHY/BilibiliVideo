package online.bingzi.bilibili.video.internal.helper

import online.bingzi.bilibili.video.internal.config.MainConfig
import taboolib.common.platform.function.console
import taboolib.module.lang.sendInfoMessage

/**
 * Debug
 * 调试日志
 *
 * @param message
 */
fun debug(message: String) {
    if (MainConfig.debugStatus) {
        console().sendInfoMessage("[Debug] > $message")
    }
}
