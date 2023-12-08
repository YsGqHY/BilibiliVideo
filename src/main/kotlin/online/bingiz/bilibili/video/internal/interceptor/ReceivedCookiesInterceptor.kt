package online.bingiz.bilibili.video.internal.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import online.bingiz.bilibili.video.internal.cache.qrCodeKeyCache

class ReceivedCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            chain.request().url().queryParameter("qrcode_key")?.let {
                qrCodeKeyCache.put(it, originalResponse.headers("Set-Cookie"))
            }
        }
        return originalResponse
    }

}
