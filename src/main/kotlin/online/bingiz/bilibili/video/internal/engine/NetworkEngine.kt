package online.bingiz.bilibili.video.internal.engine

import online.bingiz.bilibili.video.internal.engine.drive.BilibiliDrive
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network engine
 * 网络访问引擎
 *
 * @constructor Create empty Network engine
 */
object NetworkEngine {
    /**
     * Bilibili API
     * 用来构建API服务
     */
    private val bilibiliAPI: BilibiliDrive by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BilibiliDrive::class.java)
    }
}