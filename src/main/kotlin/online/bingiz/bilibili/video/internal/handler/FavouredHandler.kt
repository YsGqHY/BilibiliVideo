package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.debugStatus
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.info
import taboolib.common.platform.function.warning

/**
 * Favoured handler
 * 收藏处理器
 *
 * @constructor Create empty Favoured handler
 */
class FavouredHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        if (debugStatus) {
            info("收藏处理器 > 玩家: ${player.name} | 视频: $bvid | 接受处理")
        }
        NetworkEngine.bilibiliAPI.hasFavoured(bvid, sessData).execute().let { resultResponse ->
            if (resultResponse.isSuccessful) {
                resultResponse.body()?.data?.let {
                    if (it.favoured.not()) {
                        player.infoAsLang("GetTripleStatusFailureNotFavoured")
                        if (debugStatus) {
                            warning("收藏处理器 > 玩家: ${player.name} | 视频: $bvid | 未收藏")
                        }
                        return false
                    }
                }
            } else {
                player.infoAsLang("NetworkRequestFailureCode", resultResponse.code())
            }
        }
        if (debugStatus) {
            info("收藏处理器 > 玩家: ${player.name} | 视频: $bvid | 移交处理")
        }
        return callNextHandler(player, bvid, sessData)
    }
}
