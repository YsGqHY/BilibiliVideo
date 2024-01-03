package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer

/**
 * Favoured handler
 * 收藏处理器
 *
 * @constructor Create empty Favoured handler
 */
class FavouredHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        NetworkEngine.bilibiliAPI.hasFavoured(bvid, sessData).execute().let { resultResponse ->
            if (resultResponse.isSuccessful) {
                resultResponse.body()?.data?.let {
                    if (it.favoured.not()) {
                        player.infoAsLang("GetTripleStatusFailureNotFavoured")
                        return false
                    }
                }
            } else {
                player.infoAsLang("NetworkRequestFailureCode", resultResponse.code())
            }
        }
        return callNextHandler(player, bvid, sessData)
    }
}
