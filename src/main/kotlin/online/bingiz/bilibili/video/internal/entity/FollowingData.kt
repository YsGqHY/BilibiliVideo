package online.bingiz.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

data class FollowingData(
    @SerializedName("Card")
    val card: CardData
)
