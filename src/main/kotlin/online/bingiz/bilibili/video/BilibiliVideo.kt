package online.bingiz.bilibili.video

import online.bingiz.bilibili.video.internal.util.infoMessageAsLang
import taboolib.common.platform.Plugin

/**
 * Bilibili video
 * Bilibili 视频
 *
 * @constructor Create empty Bilibili video
 */
object BilibiliVideo : Plugin() {
    /**
     * 初始化
     */
    override fun onLoad() {
        infoMessageAsLang("Loading")
        infoMessageAsLang("Loaded")
    }

    /**
     * 启动
     *
     */
    override fun onEnable() {
        infoMessageAsLang("Enabling")
        infoMessageAsLang("Enabled")
    }

    /**
     * 关闭
     *
     */
    override fun onDisable() {
        infoMessageAsLang("Disabling")
        infoMessageAsLang("Disabled")
    }
}