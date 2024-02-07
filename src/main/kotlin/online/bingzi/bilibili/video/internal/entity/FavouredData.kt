package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Favoured data
 * 收藏数据
 */
data class FavouredData(
    @SerializedName("count")
    val count: Int,
    @SerializedName("favoured")
    val favoured: Boolean
)
