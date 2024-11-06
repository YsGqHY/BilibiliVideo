package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Poll data
 * <p>
 * 扫码结果数据
 *
 * @property url 游戏分站跨域登录 url
 * @property refreshToken    刷新refresh_token
 * @property timestamp 登录时间
 * @property code 0：扫码登录成功、86038：二维码已失效、86090：二维码已扫码未确认、86101：未扫码
 * @property message 扫码状态信息
 * @constructor Create empty Poll data
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class PollData(
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
) {
    /**
     * Is invalid
     * <p>
     * 是否超时
     *
     * @return [Boolean]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun isInvalid(): Boolean {
        return code == 86038
    }

    /**
     * Is login
     * <p>
     * 是否登录
     *
     * @return [Boolean]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    fun isLogin(): Boolean {
        return code == 0
    }
}
