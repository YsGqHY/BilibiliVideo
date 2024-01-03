package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer

/**
 * Like handler
 * 点赞处理器
 *
 * @constructor Create empty Like handler
 */
class LikeHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        NetworkEngine.bilibiliAPI.hasLike(bvid, sessData).execute().let {
            if (it.isSuccessful) {
                it.body()?.data?.let { count ->
                    if (count < 1) {
                        player.infoAsLang("GetTripleStatusFailureNotLike")
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

