package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Cookie data
 *
 * @property sessData
 * @property biliJct
 * @property dedeUserID
 * @property dedeUserIDCkMd5
 * @property sid
 * @constructor Create empty Cookie data
 */
data class CookieData(
    @SerializedName("SESSDATA")
    var sessData: String? = null,
    @SerializedName("bili_jct")
    var biliJct: String? = null,
    @SerializedName("DedeUserID")
    var dedeUserID: String? = null,
    @SerializedName("DedeUserID__ckMd5")
    var dedeUserIDCkMd5: String? = null,
    @SerializedName("sid")
    var sid: String? = null
)
