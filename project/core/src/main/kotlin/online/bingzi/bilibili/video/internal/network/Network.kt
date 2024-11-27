package online.bingzi.bilibili.video.internal.network

import okhttp3.OkHttpClient
import online.bingzi.bilibili.video.internal.interceptor.ReceivedCookiesInterceptor
import online.bingzi.bilibili.video.internal.interceptor.UserAgentInterceptor
import online.bingzi.bilibili.video.internal.network.service.ActionService
import online.bingzi.bilibili.video.internal.network.service.Buvid3Service
import online.bingzi.bilibili.video.internal.network.service.GitHubService
import online.bingzi.bilibili.video.internal.network.service.LoginService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.function.pluginId
import taboolib.common.platform.function.pluginVersion

/**
 * Network
 * <p>
 * 网络模块
 *
 * @constructor Create empty Network
 *
 * @author BingZi-233
 * @since 2.0.0
 */
@RuntimeDependencies(
    RuntimeDependency(value = "!com.squareup.okhttp3:okhttp:3.14.9", relocate = ["!kotlin.", "!kotlin1350."]),
    RuntimeDependency(value = "!com.squareup.retrofit2:retrofit:2.11.0", relocate = ["!retrofit2", "!online.bingzi.bilibili.video.library.retrofit2"]),
    RuntimeDependency(value = "!com.squareup.retrofit2:converter-gson:2.11.0", relocate = ["!retrofit2", "!online.bingzi.bilibili.video.library.retrofit2"]),
)
object Network {
    /**
     * Passport Base Url
     * <p>
     * 登录相关接口的 Base Url
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    private const val PASSPORT_BASE_URL = "https://passport.bilibili.com"

    /**
     * Api Base Url
     * <p>
     * 官方 Api 接口 Base Url
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    private const val API_BASE_URL = "https://api.bilibili.com"

    /**
     * Github Base Url
     * <p>
     * GitHub Api 接口 Base Url
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    private const val GITHUB_BASE_URL = "https://api.github.com"

    /**
     * Login client
     * <p>
     * 登录相关的 OkHttpClient
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    private val loginClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(ReceivedCookiesInterceptor())
        .addInterceptor(UserAgentInterceptor("MinecraftPlugin $pluginId/$pluginVersion(lhby233@outlook.com)")).build()

    /**
     * General client
     * <p>
     * 通用 OkHttpClient
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    private val generalClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(UserAgentInterceptor("MinecraftPlugin $pluginId/$pluginVersion(lhby233@outlook.com)")).build()

    /**
     * Login service
     * <p>
     * 登录服务
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val loginService: LoginService = Retrofit.Builder()
        .baseUrl(PASSPORT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(loginClient)
        .build()
        .create(LoginService::class.java)

    /**
     * Buvid3 service
     * <p>
     * Buvid3 服务
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val buvid3Service: Buvid3Service = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(generalClient)
        .build()
        .create(Buvid3Service::class.java)

    /**
     * Action service
     * <p>
     * 动作 服务
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val actionService: ActionService = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(generalClient)
        .build()
        .create(ActionService::class.java)

    /**
     * Github service
     * <p>
     * Github 服务
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val githubService: GitHubService = Retrofit.Builder()
        .baseUrl(GITHUB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GitHubService::class.java)
}
