package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Result VO
 * <p>
 * 返回结果VO
 *
 * @param T data type
 * @property code code
 * @property message message
 * @property ttl ttl
 * @property data data
 * @constructor Create empty Result VO
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class ResultVO<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("ttl")
    val ttl: Int,
    @SerializedName("data")
    val data: T
) {
    /**
     * Is success
     * <p>
     * 是否成功
     *
     * @return Boolean
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun isSuccess(): Boolean {
        return code == 0
    }

    /**
     * Is failed
     * <p>
     * 是否失败
     *
     * @return Boolean
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun isFailed(): Boolean {
        return isSuccess().not()
    }
}
