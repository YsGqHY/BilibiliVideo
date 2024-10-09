package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Triple link data
 * <p>
 * 三连数据
 *
 * @property like 是否点赞成功
 * @property coin 是否投币成功
 * @property fav 是否收藏成功
 * @property multiply 投币枚数，默认为2
 * @constructor Create empty Triple link data
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class TripleLinkData(
    @SerializedName("like")
    val like: Boolean,
    @SerializedName("coin")
    val coin: Boolean,
    @SerializedName("fav")
    val fav: Boolean,
    @SerializedName("multiply")
    val multiply: Int
)
