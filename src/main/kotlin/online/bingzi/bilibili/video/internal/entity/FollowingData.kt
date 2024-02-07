package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

data class FollowingData(
    @SerializedName("Card")
    val card: CardData
)
