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
    var SESSDATA: String = "",
    var bili_jct: String = "",
    var DedeUserID: String = "",
    var DedeUserID__ckMd5: String = "",
    var sid: String = ""
)
