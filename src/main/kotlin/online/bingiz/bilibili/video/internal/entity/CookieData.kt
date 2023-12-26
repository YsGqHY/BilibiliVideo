package online.bingiz.bilibili.video.internal.entity

/**
 * Cookie data
 *
 * @property SESSDATA
 * @property bili_jct
 * @property DedeUserID
 * @property DedeUserID__ckMd5
 * @property sid
 * @constructor Create empty Cookie data
 */
data class CookieData(
    var SESSDATA: String? = null,
    var bili_jct: String? = null,
    var DedeUserID: String? = null,
    var DedeUserID__ckMd5: String? = null,
    var sid: String? = null
)
