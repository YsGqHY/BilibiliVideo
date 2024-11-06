package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Generate data
 * <p>
 * 二维码生成数据
 *
 * @property url 二维码内容 (登录页面 url)
 * @property qrcodeKey 扫码登录秘钥
 * @constructor Create empty Generate data
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class GenerateData(
    @SerializedName("url")
    val url: String,
    @SerializedName("qrcode_key")
    val qrcodeKey: String
)
