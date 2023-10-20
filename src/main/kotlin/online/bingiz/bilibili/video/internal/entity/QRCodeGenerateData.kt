package online.bingiz.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * QRCode generate data
 *
 * @property url url
 * @property qrCodeKey qr code key
 * @constructor Create empty QRCode generate data
 */
data class QRCodeGenerateData(
    @SerializedName("url")
    val url: String,
    @SerializedName("qrcode_key")
    val qrCodeKey: String
)
