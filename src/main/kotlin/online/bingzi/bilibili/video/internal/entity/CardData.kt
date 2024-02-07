package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Card data
 *
 * @property following 是否关注此用户
 * @constructor Create empty Card data
 */
data class CardData(
    @SerializedName("following")
    val following: Boolean
)
