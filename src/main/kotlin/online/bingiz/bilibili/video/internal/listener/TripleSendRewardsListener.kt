package online.bingiz.bilibili.video.internal.listener

import online.bingiz.bilibili.video.api.event.TripleSendRewardsEvent
import online.bingiz.bilibili.video.internal.config.MainConfig
import online.bingiz.bilibili.video.internal.helper.ketherEval
import taboolib.common.platform.event.SubscribeEvent

object TripleSendRewardsListener {
    @SubscribeEvent
    fun onTripleSendRewardsEvent(event: TripleSendRewardsEvent) {
        MainConfig.receiveMap[event.bvid]?.ketherEval(event.player)
    }
}