package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.config.SettingConfig
import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.debugStatus
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.info
import taboolib.common.platform.function.warning

/**
 * Following handler
 * 关注处理器
 *
 * @constructor Create empty Following handler
 */
class FollowingHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        if (debugStatus) {
            info("关注处理器 > 玩家: ${player.name} | 视频: $bvid | 接受处理")
        }
        if (SettingConfig.needFollow) {
            NetworkEngine.bilibiliAPI.hasFollowing(bvid, sessData).execute().let { resultResponse ->
                if (resultResponse.isSuccessful) {
                    resultResponse.body()?.data?.let {
                        if (it.card.following.not()) {
                            player.infoAsLang("GetTripleStatusFailureNotFollowing")
                            if (debugStatus) {
                                warning("关注处理器 > 玩家: ${player.name} | 视频: $bvid | 未关注")
                            }
                            return false
                        }
                    }
                } else {
                    player.infoAsLang("NetworkRequestFailureCode", resultResponse.code())
                }
            }
        }
        if (debugStatus) {
            info("关注处理器 > 玩家: ${player.name} | 视频: $bvid | 移交处理")
        }
        return callNextHandler(player, bvid, sessData)
    }
}
