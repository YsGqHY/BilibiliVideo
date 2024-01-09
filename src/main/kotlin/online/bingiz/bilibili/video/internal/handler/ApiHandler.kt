package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.helper.debug
import online.bingiz.bilibili.video.internal.helper.debugStatus
import taboolib.common.platform.ProxyPlayer

abstract class ApiHandler {
    private var nextHandler: ApiHandler? = null

    fun setNextHandler(nextHandler: ApiHandler): ApiHandler {
        this.nextHandler = nextHandler
        return nextHandler
    }

    abstract fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean

    protected fun callNextHandler(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        return nextHandler?.handle(player, bvid, sessData) ?: let {
            if (debugStatus) {
                debug("最终处理器 > 玩家: ${player.name} | 视频: $bvid | 通过")
            }
            true
        }
    }
}
