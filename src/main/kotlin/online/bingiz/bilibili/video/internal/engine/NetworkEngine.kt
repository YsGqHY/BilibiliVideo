package online.bingiz.bilibili.video.internal.engine

import online.bingiz.bilibili.video.api.event.CookieCacheWriteEvent
import online.bingiz.bilibili.video.internal.cache.cookieCache
import online.bingiz.bilibili.video.internal.engine.drive.BilibiliDrive
import online.bingiz.bilibili.video.internal.entity.BilibiliResult
import online.bingiz.bilibili.video.internal.entity.QRCodeGenerateData
import online.bingiz.bilibili.video.internal.util.infoAsLang
import online.bingiz.bilibili.video.internal.util.toBufferedImage
import org.bukkit.entity.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored
import taboolib.module.nms.NMSMap
import taboolib.module.nms.sendMap

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

    /**
     * Generate bilibili QRCode url
     *
     * @param player
     */
    fun generateBilibiliQRCodeUrl(player: Player) {
        bilibiliAPI.applyQRCodeGenerate().enqueue(object : Callback<BilibiliResult<QRCodeGenerateData>> {
            override fun onResponse(
                call: Call<BilibiliResult<QRCodeGenerateData>>,
                response: Response<BilibiliResult<QRCodeGenerateData>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.code == 0) {
                        // 向玩家副手发送二维码地图
                        player.sendMap(body.data.url.toBufferedImage(128), NMSMap.Hand.OFF) {
                            name = "&a&lBilibili扫码登陆".colored()
                            shiny()
                            lore.clear()
                            lore.addAll(
                                listOf(
                                    "&7请使用Bilibili客户端扫描二维码",
                                    "&7二维码有效期为3分钟",
                                ).colored()
                            )
                        }
                        // 每隔1s检查一次玩家是否扫码
                        // 出现以下情况后会自动取消任务：
                        // 1. 玩家扫码登陆成功
                        // 2. 二维码已超时
                        submit(async = true, delay = 20L, period = 20L) {
                            val execute = bilibiliAPI.scanningQRCode(body.data.qrCodeKey).execute()
                            if (execute.isSuccessful) {
                                execute.body()?.let { result ->
                                    when (result.data.code) {
                                        0 -> {
                                            val list = response.headers().values("Set-Cookie")
                                            cookieCache.put(player.uniqueId, list)
                                            CookieCacheWriteEvent(player).call()
                                            player.infoAsLang("GenerateUseCookieSuccess")
                                            this.cancel()
                                        }

                                        86038 -> {
                                            player.inventory
                                            player.infoAsLang("GenerateUseCookieQRCodeTimeout")
                                            this.cancel()
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        player.infoAsLang(
                            "GenerateUseCookieFailure",
                            response.body()?.message ?: "Bilibili未提供任何错误信息"
                        )
                    }
                } else {
                    player.infoAsLang("NetworkRequestRefuse", "HTTP受限，错误码：${response.code()}")
                }
            }

            override fun onFailure(call: Call<BilibiliResult<QRCodeGenerateData>>, t: Throwable) {
                player.infoAsLang("NetworkRequestFailure", t.message ?: "未提供错误描述。")
            }
        })
    }

}