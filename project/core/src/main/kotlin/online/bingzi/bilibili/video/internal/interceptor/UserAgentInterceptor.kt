package online.bingzi.bilibili.video.internal.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * User agent interceptor
 * <p>
 * User agent拦截器
 *
 * @property userAgent user agent
 * @constructor Create empty User agent interceptor
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class UserAgentInterceptor(private val userAgent: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .removeHeader("User-Agent")
            .header("User-Agent", userAgent)
            .build()
        return chain.proceed(request)
    }
}
