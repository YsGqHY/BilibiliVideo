package online.bingiz.bilibili.video.internal.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(private val userAgent: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .removeHeader("User-Agent")
            .header("User-Agent", userAgent)
            .build()
        return chain.proceed(request)
    }
}