package online.bingiz.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Coins data
 * 投币数据
 */
data class CoinsData(
    @SerializedName("multiply")
    val multiply: Int
)
