package online.bingzi.bilibili.video.internal.entity

/**
 * Video
 *
 *
 * @author BingZi-233
 * @since 1.0.0
 * @property aid
 * @property command
 * @constructor Create empty Video
 */
data class Video(
    val aid: String,
    val command: List<String>
)
