package online.bingzi.bilibili.video.internal.network.service

import online.bingzi.bilibili.video.internal.entity.CoinsData
import online.bingzi.bilibili.video.internal.entity.FavouredData
import online.bingzi.bilibili.video.internal.entity.ResultVO
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
     * @return [Int]
     */
    @GET("/x/web-interface/archive/has/like")
    suspend fun getLike(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String
    ): Call<ResultVO<Int>>

    /**
     * Get coins
     * <p>
     * 获取投币情况
     *
     * @param bvid 稿件 bvid
     * @param sessData Cookie（SESSDATA）
     * @return [CoinsData]
     */
    @GET("/x/web-interface/archive/coins")
    suspend fun getCoins(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String
    ): Call<ResultVO<CoinsData>>

    /**
     * Get favoured
     * <p>
     * 获取收藏情况
     *
     * @param bvid 稿件 bvid
     * @param sessData Cookie（SESSDATA）
     * @return [FavouredData]
     */
    @GET("/x/v2/fav/video/favoured")
    suspend fun getFavoured(
        @Query("aid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String
    ): Call<ResultVO<FavouredData>>

    @GET("/x/web-interface/archive/like/triple")
    suspend fun actionTripleLink(
        @Query("bvid") bvid: String,
        @Header("Cookie") sessData: String,
        @Header("Cookie") buvid3: String
    ): Call<ResultVO<FavouredData>>
}
