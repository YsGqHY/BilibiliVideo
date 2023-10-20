package online.bingiz.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Result
 *
 * @param T data
 * @property code 0: success, 1: failed
 * @property message message
 * @property ttl ttl
 * @property data data
 * @constructor Create empty Result
 */
data class BilibiliResult<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("ttl")
    val ttl: Int,
    @SerializedName("data")
    val data: T
)
