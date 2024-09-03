package online.bingzi.bilibili.video.internal.entity

/**
 * Video
 * <p>
 * 视频数据结构
 * 用于记载视频对应的命令列表
 *
 *
 * @author BingZi-233
 * @since 2.0.0
 * @property bv 视频bv号
 * @property command 奖励命令列表
 * @constructor Create empty Video
 */
data class Video(
    val bv: String,
    val command: List<String>
)
