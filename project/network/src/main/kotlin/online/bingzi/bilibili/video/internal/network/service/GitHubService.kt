package online.bingzi.bilibili.video.internal.network.service

import online.bingzi.bilibili.video.internal.entity.ReleasesData
import retrofit2.http.GET

/**
 * GitHub service
 * <p>
 * GitHubAPI服务
 *
 * @constructor Create empty GitHub service
 *
 * @author BingZi-233
 * @since 2.0.0
 */
interface GitHubService {
    /**
     * Get latest release
     * <p>
     * 获取最新版本信息
     *
     * @return [ReleasesData]
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @GET("/repos/BingZi-233/BilibiliVideo/releases/latest")
    suspend fun getLatestRelease(): ReleasesData
}
