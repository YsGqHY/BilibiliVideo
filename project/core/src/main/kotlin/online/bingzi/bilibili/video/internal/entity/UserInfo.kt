package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * User info
 * <p>
 * 用户信息
 *
 * @property mid MID
 * @property uname uname
 * @constructor Create empty User info
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class UserInfo(
    @SerializedName("mid")
    val mid: String,
    @SerializedName("uname")
    val uname: String
)
