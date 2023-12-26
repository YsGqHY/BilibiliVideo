package online.bingiz.bilibili.video.internal.engine.drive

import online.bingiz.bilibili.video.internal.entity.*
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
    fun getUserInfo(
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = "buvid3=BUVID3"
    ): Call<BilibiliResult<UserInfoData>>

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
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = "buvid3=BUVID3"
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
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = "buvid3=BUVID3"
    ): Call<BilibiliResult<Int>>

    /**
     * Has coins
     * 是否被投币
     *
     * @param sessData
     * @param bvid
     */
    @GET("web-interface/archive/coins")
    fun hasCoins(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = "buvid3=BUVID3"
    ): Call<BilibiliResult<CoinsData>>

    /**
     * Has favoured
     * 是否被收藏
     *
     * @param sessData
     * @param bvid
     */
    @GET("v2/fav/video/favoured")
    fun hasFavoured(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = "buvid3=BUVID3"
    ): Call<BilibiliResult<FavouredData>>

    /**
     * Has following
     * 是否关注
     *
     * @param sessData
     * @param bvid
     * @return
     */
    @GET("web-interface/view/detail")
    fun hasFollowing(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = "buvid3=BUVID3"
    ): Call<BilibiliResult<FollowingData>>
}