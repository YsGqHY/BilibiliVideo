package online.bingzi.bilibili.video.internal.helper

import taboolib.common.platform.function.info
import taboolib.module.chat.colored

/**
 * Debug
 * Debug Helper
 *
 * @param message Message
 */
internal fun debug(message: String) {
    info("&7&l[&a&ldebug&7&l] > &f&l$message".colored())
}
