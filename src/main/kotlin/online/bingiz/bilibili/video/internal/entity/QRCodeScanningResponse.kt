package online.bingiz.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * QRCode scanning response
 *
 * @property code 0: success, 1: failed
 * @property message message
 * @property ttl ttl
 * @property data data
 * @constructor Create empty Q r code scanning response
 */
data class QRCodeScanningResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("ttl")
    val ttl: Int,
    @SerializedName("data")
    val data: Data
) {
    /**
     * Data
     *
     * @property url url
     * @property refreshToken refresh token
     * @property timestamp timestamp
     * @property code code
     * @property message message
     * @constructor Create empty Data
     */
    data class Data(
        @SerializedName("url")
        val url: String,
        @SerializedName("refresh_token")
        val refreshToken: String,
        @SerializedName("timestamp")
        val timestamp: Int,
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String
    )
}
