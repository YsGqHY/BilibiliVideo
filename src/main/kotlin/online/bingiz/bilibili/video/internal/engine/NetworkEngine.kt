package online.bingiz.bilibili.video.internal.engine

import online.bingiz.bilibili.video.api.event.TripleSendRewardsEvent
import online.bingiz.bilibili.video.internal.cache.cookieCache
import online.bingiz.bilibili.video.internal.engine.drive.BilibiliApiDrive
import online.bingiz.bilibili.video.internal.engine.drive.BilibiliPassportDrive
import online.bingiz.bilibili.video.internal.entity.BilibiliResult
import online.bingiz.bilibili.video.internal.entity.QRCodeGenerateData
import online.bingiz.bilibili.video.internal.entity.TripleData
import online.bingiz.bilibili.video.internal.helper.DatabaseHelper
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import online.bingiz.bilibili.video.internal.helper.toBufferedImage
import org.bukkit.entity.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import taboolib.common.platform.function.submit
import taboolib.expansion.getDataContainer
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
     * 哔哩哔哩API驱动
     */
    private val bilibiliAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.bilibili.com/x")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BilibiliApiDrive::class.java)
    }

    /**
     * Bilibili passport API
     * 哔哩哔哩通行证驱动API
     */
    private val bilibiliPassportAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://passport.bilibili.com/x")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BilibiliPassportDrive::class.java)
    }

    /**
     * Bilibili website API
     * 哔哩哔哩网站驱动API
     */
    private val bilibiliWebsiteAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.bilibili.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BilibiliPassportDrive::class.java)
    }

    /**
     * Generate bilibili QRCode url
     *
     * @param player
     */
    fun generateBilibiliQRCodeUrl(player: Player) {
        bilibiliPassportAPI.applyQRCodeGenerate().enqueue(object : Callback<BilibiliResult<QRCodeGenerateData>> {
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
                                            val mid = checkRepeatabilityMid(list)
                                            // 检查重复的MID
                                            if (mid == null) {
                                                player.infoAsLang("GenerateUseCookieRepeatabilityMid")
                                            } else {
                                                cookieCache.put(player.uniqueId, list)
                                                player.getDataContainer()["mid"] = mid
                                                player.getDataContainer()["refresh_token"] = result.data.refreshToken
                                                player.getDataContainer()["timestamp"] = result.data.timestamp
                                                player.infoAsLang("GenerateUseCookieSuccess")
                                            }
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

    /**
     * Get triple status
     * 获取三连状态
     *
     * @param player 玩家
     * @param bvid  视频BV号
     */
    fun getTripleStatus(player: Player, bvid: String) {
        cookieCache[player.uniqueId]?.first { it.startsWith("bili_jct=") }?.let {
            bilibiliPassportAPI.actionLikeTriple("", bvid, it.replace("bili_jct", "csrf"))
                .enqueue(object : Callback<BilibiliResult<TripleData>> {
                    override fun onResponse(
                        call: Call<BilibiliResult<TripleData>>,
                        response: Response<BilibiliResult<TripleData>>
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            if (body != null) {
                                when (body.code) {
                                    0 -> {
                                        val tripleData = body.data
                                        if (tripleData.coin && tripleData.fav && tripleData.like) {
                                            TripleSendRewardsEvent(player, bvid).call()
                                        } else {
                                            player.infoAsLang(
                                                "GetTripleStatusFailure",
                                                tripleData.like,
                                                tripleData.coin,
                                                tripleData.multiply,
                                                tripleData.fav
                                            )
                                        }
                                    }

                                    -101 -> {
                                        player.infoAsLang("GetTripleStatusCookieInvalid")
                                    }

                                    10003 -> {
                                        player.infoAsLang("GetTripleStatusTargetFailed")
                                    }

                                    else -> {
                                        player.infoAsLang(
                                            "GetTripleStatusFailure",
                                            response.body()?.message ?: "Bilibili未提供任何错误信息"
                                        )
                                    }
                                }
                            } else {
                                player.infoAsLang(
                                    "GetTripleStatusRefuse",
                                    response.body()?.message ?: "Bilibili未提供任何错误信息"
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<BilibiliResult<TripleData>>, t: Throwable) {
                        player.infoAsLang("NetworkRequestFailure", t.message ?: "Bilibili未提供任何错误信息。")
                    }
                })
        } ?: player.infoAsLang("GetTripleStatusCookieInvalid")
    }

    /**
     * Check repeatability mid
     * 检查重复的MID
     *
     * @param cookie cookie
     */
    fun checkRepeatabilityMid(cookie: List<String>): String? {
        // 获取 SASSDATA
        val sassData = cookie.find { it.startsWith("SASSDATA") } ?: return null
        // 获取用户信息
        val response = bilibiliAPI.getUserInfo(sassData).execute()
        // 判断请求是否成功并且返回的数据 code 是否为 0
        return when {
            response.isSuccessful && response.body()?.code == 0 -> {
                // 获取 MID
                val mid = response.body()?.data?.mid ?: return null
                // 如果数据库中存在该 MID 则返回 null，否则返回 MID
                if (DatabaseHelper.searchPlayerByMid(mid)) null else mid
            }

            else -> null
        }
    }


}