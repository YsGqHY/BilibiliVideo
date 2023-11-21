package online.bingiz.bilibili.video.internal.engine

import okhttp3.OkHttpClient
import online.bingiz.bilibili.video.api.event.TripleSendRewardsEvent
import online.bingiz.bilibili.video.internal.cache.bvCache
import online.bingiz.bilibili.video.internal.cache.cookieCache
import online.bingiz.bilibili.video.internal.cache.qrCodeKeyCache
import online.bingiz.bilibili.video.internal.engine.drive.BilibiliApiDrive
import online.bingiz.bilibili.video.internal.engine.drive.BilibiliPassportDrive
import online.bingiz.bilibili.video.internal.entity.BilibiliResult
import online.bingiz.bilibili.video.internal.entity.QRCodeGenerateData
import online.bingiz.bilibili.video.internal.entity.TripleData
import online.bingiz.bilibili.video.internal.helper.DatabaseHelper
import online.bingiz.bilibili.video.internal.helper.infoAsLang
import online.bingiz.bilibili.video.internal.helper.sendMapVersionCompatible
import online.bingiz.bilibili.video.internal.helper.toBufferedImage
import online.bingiz.bilibili.video.internal.interceptor.ReceivedCookiesInterceptor
import online.bingiz.bilibili.video.internal.interceptor.UserAgentInterceptor
import org.bukkit.entity.Player
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import taboolib.common.platform.function.pluginId
import taboolib.common.platform.function.pluginVersion
import taboolib.common.platform.function.submit
import taboolib.common.platform.function.warning
import taboolib.expansion.getDataContainer
import taboolib.module.chat.colored
import taboolib.module.nms.NMSMap

/**
 * Network engine
 * 网络访问引擎
 *
 * @constructor Create empty Network engine
 */
object NetworkEngine {
    private val client = OkHttpClient.Builder()
        .addInterceptor(ReceivedCookiesInterceptor())
        .addInterceptor(UserAgentInterceptor("MinecraftPlugin $pluginId/$pluginVersion(lhby233@outlook.com)"))
        .build()

    /**
     * Bilibili API
     * 哔哩哔哩API驱动
     */
    private val bilibiliAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.bilibili.com/x/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(BilibiliApiDrive::class.java)
    }

    /**
     * Bilibili passport API
     * 哔哩哔哩通行证驱动API
     */
    private val bilibiliPassportAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://passport.bilibili.com/x/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(BilibiliPassportDrive::class.java)
    }

    /**
     * Bilibili website API
     * 哔哩哔哩网站驱动API
     */
    private val bilibiliWebsiteAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.bilibili.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
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
                        player.sendMapVersionCompatible(body.data.url.toBufferedImage(128), NMSMap.Hand.MAIN) {
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
                        submit(async = true, delay = 20L * 9, period = 20L * 3) {
                            val qrCodeKey = body.data.qrCodeKey
                            val execute = bilibiliPassportAPI.scanningQRCode(qrCodeKey).execute()
                            if (execute.isSuccessful) {
                                execute.body()?.let { result ->
                                    when (result.data.code) {
                                        0 -> {
                                            qrCodeKeyCache[qrCodeKey]?.let {
                                                val mid = checkRepeatabilityMid(player, it)
                                                // 检查重复的MID
                                                if (mid == null) {
                                                    player.infoAsLang("GenerateUseCookieRepeatabilityMid")
                                                } else {
                                                    cookieCache.put(player.uniqueId, it)
                                                    player.getDataContainer()["mid"] = mid
                                                    player.getDataContainer()["refresh_token"] =
                                                        result.data.refreshToken
                                                    player.getDataContainer()["timestamp"] = result.data.timestamp
                                                    player.infoAsLang("GenerateUseCookieSuccess")
                                                }
                                                this.cancel()
                                            }
                                        }

                                        86038 -> {
                                            player.infoAsLang("GenerateUseCookieQRCodeTimeout")
                                            this.cancel()
                                        }

                                        else -> {
                                            return@submit
                                        }
                                    }
                                }
                                player.updateInventory()
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
        bvCache[player.uniqueId to bvid]?.let {
            if (it) {
                player.infoAsLang("GetTripleStatusRepeat")
                return
            }
        }
        val csrf = cookieCache[player.uniqueId]?.first { it.startsWith("bili_jct=") }
            ?.replace("bili_jct=", "")
            ?.split(";")
            ?.get(0)
        val sessData = cookieCache[player.uniqueId]?.first { it.startsWith("SESSDATA") }
            ?.split(";")
            ?.get(0) + ",bili_jct=${csrf},buvid3=BUVID3"
        if (csrf != null) {
            bilibiliAPI.actionLikeTriple(bvid, csrf, sessData)
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
                                            player.getDataContainer()[bvid] = true
                                            bvCache.put(player.uniqueId to bvid, true)
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
                                            "GetTripleStatusError",
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
                        } else {
                            warning("请求失败")
                            warning("失败原因：${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<BilibiliResult<TripleData>>, t: Throwable) {
                        player.infoAsLang("NetworkRequestFailure", t.message ?: "Bilibili未提供任何错误信息。")
                    }
                })
        }
    }

    /**
     * Check repeatability mid
     * 检查重复的MID
     *
     * @param cookie cookie
     */
    fun checkRepeatabilityMid(player: Player, cookie: List<String>): String? {
        // 获取 SASSDATA
        val sessData = cookie.find { it.startsWith("SESSDATA") } ?: return null
        // 获取用户信息
        val response = bilibiliAPI.getUserInfo(sessData).execute()
        // 判断请求是否成功并且返回的数据 code 是否为 0
        return when {
            response.isSuccessful -> {
                // 获取 MID
                val mid = response.body()?.data?.mid ?: return null
                // 如果数据库中存在该 MID 则返回 null，否则返回 MID
                if (DatabaseHelper.searchPlayerByMid(player, mid)) null else mid
            }

            else -> null
        }
    }


}