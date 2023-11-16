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

    /**
     * Has like
     * 是否被点赞
     *
     * @param sessData
     * @param bvid
     */
    @GET("web-interface/archive/has/like")
    fun hasLike(
        @Header("Cookie") sessData: String,
        @Query("bvid") bvid: String
    )

    /**
     * Has coins
     * 是否被投币
     *
     * @param sessData
     * @param bvid
     */
    @GET("web-interface/archive/coins")
    fun hasCoins(
        @Header("Cookie") sessData: String,
        @Query("bvid") bvid: String
    )

    /**
     * Has favoured
     * 是否被收藏
     *
     * @param sessData
     * @param bvid
     */
    @GET("v2/fav/video/favoured")
    fun hasFavoured(
        @Header("Cookie") sessData: String,
        @Query("bvid") bvid: String
    )
}