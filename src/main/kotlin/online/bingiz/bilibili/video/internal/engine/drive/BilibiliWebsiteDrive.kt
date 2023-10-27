package online.bingiz.bilibili.video.internal.engine.drive

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Bilibili website drive
 * 哔哩哔哩网站驱动
 *
 * @constructor Create empty Bilibili website drive
 */
interface BilibiliWebsiteDrive {

    /**
     * Get refresh CSRF
     *
     * @return
     */
    @GET("correspond/1/{correspondPath}")
    fun getRefreshCSRF(@Path("correspondPath") correspondPath: String): Call<ResponseBody>
}
