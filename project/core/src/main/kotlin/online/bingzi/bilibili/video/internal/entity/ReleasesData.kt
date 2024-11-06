package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Releases data
 * <p>
 * 最新版本数据
 *
 * @property tagName Tag名
 * @property targetCommitish 分支名
 * @property name 版本名
 * @property createdAt 创建时间
 * @property assets 资源列表
 * @constructor Create empty Releases data
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class ReleasesData(
    @SerializedName("tag_name")
    val tagName: String,
    @SerializedName("target_commitish")
    val targetCommitish: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("assets")
    val assets: List<AssetsData>
)
