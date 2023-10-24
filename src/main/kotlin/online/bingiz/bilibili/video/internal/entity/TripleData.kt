package online.bingiz.bilibili.video.internal.entity

/**
 * Triple data
 * 三连数据
 *
 * @property like 点赞
 * @property coin 投币
 * @property fav 收藏
 * @property multiply 硬币数
 * @constructor Create empty Triple data
 */
data class TripleData(
    val like: Boolean,
    val coin: Boolean,
    val fav: Boolean,
    val multiply: Int
)
