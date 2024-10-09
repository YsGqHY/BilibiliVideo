package online.bingzi.bilibili.video.internal.cache

/**
 * Network cache
 * <p>
 * 网络缓存
 *
 * @constructor Create empty Network cache
 *
 * @author BingZi-233
 * @since 2.0.0
 */
internal object NetworkCache {
    /**
     * Login cookie cache
     * <p>
     * 登录Cookie缓存
     * 用于完成登录时响应的Set-Cookie临时存储
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val loginCookieCache = mutableMapOf<String, List<String>>()
}
