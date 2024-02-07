package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * QRCode scanning data
 *
 * @property url url
 * @property refreshToken refresh token
 * @property timestamp timestamp
 * @property code 0: success, 1: failed
 * @property message message
 * @constructor Create empty QRCode scanning data
 */
data class QRCodeScanningData(
    @SerializedName("url")
    val url: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)
