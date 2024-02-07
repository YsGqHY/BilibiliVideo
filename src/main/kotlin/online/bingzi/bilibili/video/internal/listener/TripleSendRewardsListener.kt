package online.bingzi.bilibili.video.internal.listener

import online.bingzi.bilibili.video.internal.config.VideoConfig
import online.bingzi.bilibili.video.internal.database.Database.Companion.setDataContainer
import online.bingzi.bilibili.video.internal.helper.ketherEval
import taboolib.common.platform.event.SubscribeEvent

object TripleSendRewardsListener {
    @SubscribeEvent
    fun onTripleSendRewardsEvent(event: online.bingzi.bilibili.video.api.event.TripleSendRewardsEvent) {
        VideoConfig.receiveMap[event.bvid]?.ketherEval(event.player)
        // 完成数据保存
        event.player.setDataContainer(event.bvid, true.toString())
        online.bingzi.bilibili.video.internal.cache.bvCache.put(event.player.uniqueId to event.bvid, true)
    }
}
