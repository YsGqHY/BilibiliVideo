package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.debugStatus
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.info
import taboolib.common.platform.function.warning

/**
 * Coins handler
 * 硬币处理器
 *
 * @constructor Create empty Coins handler
 */
class CoinsHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        if (debugStatus) {
            info("硬币处理器 > 玩家: ${player.name} | 视频: $bvid | 接受处理")
        }
        NetworkEngine.bilibiliAPI.hasCoins(bvid, sessData).execute().let {
            if (it.isSuccessful) {
                it.body()?.data?.multiply?.let { count ->
                    if (count < 1) {
                        player.infoAsLang("GetTripleStatusFailureNotCoins")
                        if (debugStatus) {
                            warning("硬币处理器 > 玩家: ${player.name} | 视频: $bvid | 硬币不足")
                        }
                        return false
                    }
                }
            } else {
                player.infoAsLang("NetworkRequestFailureCode", it.code())
            }
        }
        if (debugStatus) {
            info("硬币处理器 > 玩家: ${player.name} | 视频: $bvid | 移交处理")
        }
        return callNextHandler(player, bvid, sessData)
    }
}
