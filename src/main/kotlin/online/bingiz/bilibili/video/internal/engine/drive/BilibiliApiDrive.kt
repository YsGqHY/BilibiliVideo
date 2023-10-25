package online.bingiz.bilibili.video.internal.engine.drive

import online.bingiz.bilibili.video.internal.entity.BilibiliResult
import online.bingiz.bilibili.video.internal.entity.QRCodeScanningData
import online.bingiz.bilibili.video.internal.entity.UserInfoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Bilibili api drive
 * 哔哩哔哩API驱动
 *
 * @constructor Create empty Bilibili api drive
 */
interface BilibiliApiDrive {

    /**
     * Scanning QRCode
     *
     * @param key
     * @return
     */
    @GET("/passport-login/web/qrcode/poll")
    fun scanningQRCode(@Query("qrcode_key") key: String): Call<BilibiliResult<QRCodeScanningData>>

    @GET("/web-interface/nav")
    fun getUserInfo(@Header("Cookie") sessData: String): Call<BilibiliResult<UserInfoData>>
}