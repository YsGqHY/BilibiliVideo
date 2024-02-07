package online.bingzi.bilibili.video.internal.handler

import online.bingzi.bilibili.video.internal.engine.NetworkEngine
import online.bingzi.bilibili.video.internal.helper.debug
import online.bingzi.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer

/**
 * Favoured handler
 * 收藏处理器
 *
 * @constructor Create empty Favoured handler
 */
class FavouredHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        debug("收藏处理器 > 玩家: ${player.name} | 视频: $bvid | 接受处理")
        NetworkEngine.bilibiliAPI.hasFavoured(bvid, sessData).execute().let { resultResponse ->
            if (resultResponse.isSuccessful) {
                resultResponse.body()?.data?.let {
                    if (it.favoured.not()) {
                        player.infoAsLang("GetTripleStatusFailureNotFavoured")
                        debug("收藏处理器 > 玩家: ${player.name} | 视频: $bvid | 未收藏")
                        return false
                    }
                }
            } else {
                player.infoAsLang("NetworkRequestFailureCode", resultResponse.code())
            }
        }
        debug("收藏处理器 > 玩家: ${player.name} | 视频: $bvid | 移交处理")
        return callNextHandler(player, bvid, sessData)
    }
}
