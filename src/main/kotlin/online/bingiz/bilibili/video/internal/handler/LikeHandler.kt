package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.debugStatus
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.info
import taboolib.common.platform.function.warning

/**
 * Like handler
 * 点赞处理器
 *
 * @constructor Create empty Like handler
 */
class LikeHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        if (debugStatus) {
            info("点赞处理器 > 玩家: ${player.name} | 视频: $bvid | 接受处理")
        }
        NetworkEngine.bilibiliAPI.hasLike(bvid, sessData).execute().let {
            if (it.isSuccessful) {
                it.body()?.data?.let { count ->
                    if (count < 1) {
                        player.infoAsLang("GetTripleStatusFailureNotLike")
                        if (debugStatus) {
                            warning("点赞处理器 > 玩家: ${player.name} | 视频: $bvid | 未点赞")
                        }
                        return false
                    }
                }
            } else {
                player.infoAsLang("NetworkRequestFailureCode", it.code())
            }
        }
        if (debugStatus) {
            info("点赞处理器 > 玩家: ${player.name} | 视频: $bvid | 移交处理")
        }
        return callNextHandler(player, bvid, sessData)
    }
}

