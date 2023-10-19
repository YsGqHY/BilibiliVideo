package online.bingiz.bilibili.video.internal.engine.drive

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Bilibili drive
 * 哔哩哔哩驱动
 *
 * @constructor Create empty Bilibili drive
 */
interface BilibiliDrive {
    @GET("https://passport.bilibili.com/x/passport-login/web/qrcode/generate")
    fun applyQRCodeGenerate(): Call<ResponseBody>

    @GET("https://api.bilibili.com/x/passport-login/web/qrcode/poll")
    fun scanningQRCode(@Query("qrcode_key") key: String): Call<ResponseBody>

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("https://passport.bilibili.com/x/web-interface/archive/like/triple")
    fun actionLikeTriple(
        @Field("aid") aid: String, @Field("bvid") bvid: String, @Field("csrf") csrf: String
    ): Call<ResponseBody>
}