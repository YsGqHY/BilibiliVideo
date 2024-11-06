package online.bingzi.bilibili.video.internal.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import online.bingzi.bilibili.video.internal.cache.Cache

/**
 * Received cookies interceptor
 * <p>
 * 接收Cookie拦截器
 *
 * @constructor Create empty Received cookies interceptor
 *
 * @author BingZi-233
 * @since 2.0.0
 */
class ReceivedCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        val headers = originalResponse.headers("Set-Cookie")
        if (headers.isNotEmpty()) {
            chain.request().url().queryParameter("qrcode_key")?.let {
                Cache.loginCookieCache.put(it, headers)
            }
        }
        return originalResponse
    }
}
