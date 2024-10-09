package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Coins data
 * <p>
 * 投币数据
 *
 * @property multiply 投币数量
 * @constructor Create empty Coins data
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class CoinsData(
    @SerializedName("multiply")
    val multiply: Int
)
