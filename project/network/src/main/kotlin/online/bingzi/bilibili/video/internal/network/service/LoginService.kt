package online.bingzi.bilibili.video.internal.network.service

import online.bingzi.bilibili.video.internal.entity.GenerateData
import online.bingzi.bilibili.video.internal.entity.PollData
import online.bingzi.bilibili.video.internal.entity.ResultVO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Login service
 * <p>
 * 登录服务
 *
 * @constructor Create empty Login service
 *
 * @author BingZi-233
 * @since 2.0.0
 */
interface LoginService {
    /**
     * Generate
     * <p>
     * 生成二维码
     *
     * @return [GenerateData]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @GET("/x/passport-login/web/qrcode/generate")
    suspend fun generate(): Call<ResultVO<GenerateData>>

    /**
     * Poll
     * <p>
     * 获取扫码结果
     *
     * @param qrcodeKey 二维码秘钥
     * @return [PollData]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @GET("/x/passport-login/web/qrcode/poll")
    suspend fun poll(@Query("qrcode_key") qrcodeKey: String): Call<ResultVO<PollData>>
}
