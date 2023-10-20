package online.bingiz.bilibili.video.internal.engine.drive

import okhttp3.ResponseBody
import online.bingiz.bilibili.video.internal.entity.QRCodeGenerateResponse
import online.bingiz.bilibili.video.internal.entity.QRCodeScanningResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Bilibili drive
 * 哔哩哔哩驱动
 *
 * @constructor Create empty Bilibili drive
 */
interface BilibiliDrive {
    /**
     * Apply QRCode generate
     *
     * @return
     */
    @GET("https://passport.bilibili.com/x/passport-login/web/qrcode/generate")
    fun applyQRCodeGenerate(): Call<QRCodeGenerateResponse>

    /**
     * Scanning QRCode
     *
     * @param key
     * @return
     */
    @GET("https://api.bilibili.com/x/passport-login/web/qrcode/poll")
    fun scanningQRCode(@Query("qrcode_key") key: String): Call<QRCodeScanningResponse>

    /**
     * Action like triple
     *
     * @param aid
     * @param bvid
     * @param csrf
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("https://passport.bilibili.com/x/web-interface/archive/like/triple")
    fun actionLikeTriple(
        @Field("aid") aid: String,
        @Field("bvid") bvid: String,
        @Field("csrf") csrf: String
    ): Call<ResponseBody>

    /**
     * Check cookie refresh token
     *
     * @param csrf
     * @return
     */
    @GET("https://passport.bilibili.com/x/passport-login/web/cookie/info")
    fun checkCookieRefreshToken(@Query("csrf") csrf: String): Call<ResponseBody>

    /**
     * Get refresh CSRF
     *
     * @return
     */
    @GET("https://www.bilibili.com/correspond/1/{correspondPath}")
    fun getRefreshCSRF(@Path("correspondPath") correspondPath: String): Call<ResponseBody>

    /**
     * Refresh cookie
     *
     * @return
     */
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("https://passport.bilibili.com/x/passport-login/web/cookie/refresh")
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
    @POST("https://passport.bilibili.com/x/passport-login/web/confirm/refresh")
    fun confirmRefreshCookie(
        @Field("csrf") csrf: String,
        @Field("refresh_token") refreshToken: String
    ): Call<ResponseBody>
}