package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer

/**
 * Coins handler
 * 硬币处理器
 *
 * @constructor Create empty Coins handler
 */
class CoinsHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        NetworkEngine.bilibiliAPI.hasCoins(bvid, sessData).execute().let {
            if (it.isSuccessful) {
                it.body()?.data?.multiply?.let { count ->
                    if (count < 1) {
                        player.infoAsLang("GetTripleStatusFailureNotCoins")
                        return false
                    }
                }
            } else {
                player.infoAsLang("NetworkRequestFailureCode", it.code())
            }
        }
        return callNextHandler(player, bvid, sessData)
    }
}
