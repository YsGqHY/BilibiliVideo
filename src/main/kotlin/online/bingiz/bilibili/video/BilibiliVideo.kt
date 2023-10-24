package online.bingiz.bilibili.video

import online.bingiz.bilibili.video.internal.util.infoMessageAsLang
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Plugin
import taboolib.expansion.setupPlayerDatabase

/**
 * Bilibili video
 * Bilibili 视频
 *
 * @constructor Create empty Bilibili video
 */
@RuntimeDependencies(
    RuntimeDependency("com.squareup.retrofit2:retrofit:2.9.0"),
    RuntimeDependency("com.squareup.retrofit2:converter-gson:2.9.0"),
    RuntimeDependency("com.google.zxing:core:3.5.2"),
    RuntimeDependency("com.google.code.gson:gson:2.10.1"),
)
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
        infoMessageAsLang("Database")
        setupPlayerDatabase()
        infoMessageAsLang("Databased")
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