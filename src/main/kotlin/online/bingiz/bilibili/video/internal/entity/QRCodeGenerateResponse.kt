package online.bingiz.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * QRCode generate response
 *
 * @property code 0: success, 1: failed
 * @property message message
 * @property ttl ttl
 * @property data data
 * @constructor Create empty Q r code generate response
 */
data class QRCodeGenerateResponse(
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
     * @property qrcodeKey qrcode key
     * @constructor Create empty Data
     */
    data class Data(
        @SerializedName("url")
        val url: String,
        @SerializedName("qrcode_key")
        val qrCodeKey: String
    )
}
