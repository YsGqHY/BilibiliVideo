package online.bingiz.bilibili.video.internal.listener

import online.bingiz.bilibili.video.api.event.TripleSendRewardsEvent
import online.bingiz.bilibili.video.internal.cache.bvCache
import online.bingiz.bilibili.video.internal.config.VideoConfig
import online.bingiz.bilibili.video.internal.database.Database.Companion.setDataContainer
import online.bingiz.bilibili.video.internal.helper.ketherEval
import taboolib.common.platform.event.SubscribeEvent

object TripleSendRewardsListener {
    @SubscribeEvent
    fun onTripleSendRewardsEvent(event: TripleSendRewardsEvent) {
        VideoConfig.receiveMap[event.bvid]?.ketherEval(event.player)
        // 完成数据保存
        event.player.setDataContainer(event.bvid, true.toString())
        bvCache.put(event.player.uniqueId to event.bvid, true)
    }
}