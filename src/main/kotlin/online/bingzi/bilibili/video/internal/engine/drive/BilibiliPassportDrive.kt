package online.bingzi.bilibili.video.internal.engine.drive

import okhttp3.ResponseBody
import online.bingzi.bilibili.video.internal.entity.BilibiliResult
import online.bingzi.bilibili.video.internal.entity.QRCodeGenerateData
import online.bingzi.bilibili.video.internal.entity.QRCodeScanningData
import retrofit2.Call
import retrofit2.http.*

/**
 * Bilibili passport drive
 * 哔哩哔哩通行证驱动
 *
 * @constructor Create empty Bilibili passport drive
 */
interface BilibiliPassportDrive {
    /**
     * Apply QRCode generate
     *
     * @return
     */
    @GET("passport-login/web/qrcode/generate")
    fun applyQRCodeGenerate(): Call<BilibiliResult<QRCodeGenerateData>>

    /**
     * Scanning QRCode
     *
     * @param key
     * @return
     */
    @GET("passport-login/web/qrcode/poll")
    fun scanningQRCode(@Query("qrcode_key") key: String): Call<BilibiliResult<QRCodeScanningData>>

    /**
     * Check cookie refresh token
     *
     * @param csrf
     * @return
     */
    @GET("passport-login/web/cookie/info")
    fun checkCookieRefreshToken(@Query("csrf") csrf: String): Call<ResponseBody>

    /**
     * Refresh cookie
     *
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("passport-login/web/cookie/refresh")
    fun refreshCookie(
        @Field("csrf") csrf: String,
        @Field("refresh_csrf") refreshCsrf: String,
        @Field("source") source: String = "main_web",
        @Field("refresh_token") refreshToken: String
    ): Call<ResponseBody>

    /**
     * Confirm refresh cookie
     *
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("passport-login/web/confirm/refresh")
    fun confirmRefreshCookie(
        @Field("csrf") csrf: String,
        @Field("refresh_token") refreshToken: String
    ): Call<ResponseBody>
}
