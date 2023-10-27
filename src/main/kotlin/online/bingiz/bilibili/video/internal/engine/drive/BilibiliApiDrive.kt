package online.bingiz.bilibili.video.internal.engine.drive

import online.bingiz.bilibili.video.internal.entity.BilibiliResult
import online.bingiz.bilibili.video.internal.entity.TripleData
import online.bingiz.bilibili.video.internal.entity.UserInfoData
import retrofit2.Call
import retrofit2.http.*

/**
 * Bilibili api drive
 * 哔哩哔哩API驱动
 *
 * @constructor Create empty Bilibili api drive
 */
interface BilibiliApiDrive {

    @GET("web-interface/nav")
    fun getUserInfo(@Header("Cookie") sessData: String): Call<BilibiliResult<UserInfoData>>

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
    @POST("web-interface/archive/like/triple")
    fun actionLikeTriple(
        @Field("bvid") bvid: String,
        @Field("csrf") csrf: String,
        @Header("Cookie") sessData: String
    ): Call<BilibiliResult<TripleData>>
}