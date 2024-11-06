package online.bingzi.bilibili.video.internal.network.service

import online.bingzi.bilibili.video.internal.entity.Buvid3Data
import online.bingzi.bilibili.video.internal.entity.ResultVO
import retrofit2.Call
import retrofit2.http.GET

/**
 * Buvid3service
 * <p>
 * Buvid3获取服务
 *
 * @constructor Create empty Buvid3service
 *
 * @author BingZi-233
 * @since 2.0.0
 */
interface Buvid3Service {
    /**
     * Get buvid3
     * <p>
     * 获取Buvid3
     *
     * @return [Buvid3Data]
     */
    @GET("/x/web-frontend/getbuvid")
    fun getBuvid3(): Call<ResultVO<Buvid3Data>>
}
