package online.bingiz.bilibili.video.internal.handler

import online.bingiz.bilibili.video.internal.config.SettingConfig
import online.bingiz.bilibili.video.internal.engine.NetworkEngine
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import taboolib.common.platform.ProxyPlayer

/**
 * Following handler
 * 关注处理器
 *
 * @constructor Create empty Following handler
 */
class FollowingHandler : ApiHandler() {
    override fun handle(player: ProxyPlayer, bvid: String, sessData: String): Boolean {
        if (SettingConfig.needFollow) {
            NetworkEngine.bilibiliAPI.hasFollowing(bvid, sessData).execute().let { resultResponse ->
                if (resultResponse.isSuccessful) {
                    resultResponse.body()?.data?.let {
                        if (it.card.following.not()) {
                            player.infoAsLang("GetTripleStatusFailureNotFollowing")
                            return false
                        }
                    }
                } else {
                    player.infoAsLang("NetworkRequestFailureCode", resultResponse.code())
                }
            }
        }
        return callNextHandler(player, bvid, sessData)
    }
}
