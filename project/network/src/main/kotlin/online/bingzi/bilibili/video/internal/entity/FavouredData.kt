package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Favoured data
 * <p>
 * 收藏数据
 *
 * @property count 作用不明
 * @property favoured 是否收藏
 * @constructor Create empty Favoured data
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class FavouredData(
    @SerializedName("count")
    val count: Int,
    @SerializedName("favoured")
    val favoured: Boolean
)
