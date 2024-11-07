package online.bingzi.bilibili.video.internal.network.service

import online.bingzi.bilibili.video.internal.cache.Cache
import online.bingzi.bilibili.video.internal.entity.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Action service
 * <p>
 * 动作服务
 *
 * @constructor Create empty Action service
 *
 * @author BingZi-233
 * @since 2.0.0
 */
interface ActionService {
    /**
     * Get like
     * <p>
     * 获取点赞情况
     * 这一 API 实际上只能判断出视频在近期内是否被点赞, 并不能判断出视频是否被点赞. 「近期」的定义不明, 但至少半年前点赞过的视频, 用这一接口获取到的结果就已经是 0 了.
     *
     * @param bvid 稿件 bvid
     * @param sessData Cookie（SESSDATA）
     * @param buvid3 Buvid3
     * @return [Call]
     *
     * @see [ResultVO]
     * @see [Int]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @GET("/x/web-interface/archive/has/like")
    fun getLike(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = Cache.buvid3
    ): Call<ResultVO<Int>>

    /**
     * Get coins
     * <p>
     * 获取投币情况
     *
     * @param bvid 稿件 bvid
     * @param sessData Cookie（SESSDATA）
     * @param buvid3 Buvid3
     * @return [Call]
     *
     * @see [ResultVO]
     * @see [CoinsData]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @GET("/x/web-interface/archive/coins")
    fun getCoins(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = Cache.buvid3
    ): Call<ResultVO<CoinsData>>

    /**
     * Get favoured
     * <p>
     * 获取收藏情况
     *
     * @param bvid 稿件 bvid
     * @param sessData Cookie（SESSDATA）
     * @param buvid3 Buvid3
     * @return [Call]
     *
     * @see [ResultVO]
     * @see [FavouredData]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @GET("/x/v2/fav/video/favoured")
    fun getFavoured(
        @Query("aid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = Cache.buvid3
    ): Call<ResultVO<FavouredData>>

    /**
     * Action triple link
     * <p>
     * 三连动作
     *
     * @param bvid 稿件 bvid
     * @param sessData Cookie（SESSDATA）
     * @param buvid3 Buvid3
     * @return [Call]
     *
     * @see [ResultVO]
     * @see [TripleLinkData]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @GET("/x/web-interface/archive/like/triple")
    fun actionTripleLink(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String = Cache.buvid3
    ): Call<ResultVO<TripleLinkData>>

    /**
     * Get user info
     * <p>
     * 获取用户信息
     *
     * @param sessData Cookie（SESSDATA）
     * @return [Call]
     *
     * @see [ResultVO]
     * @see [UserInfo]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @GET("/x/web-interface/nav")
    fun getUserInfo(
        @Query("Cookie") sessData: String
    ): Call<ResultVO<UserInfo>>
}
