package online.bingzi.bilibili.video.internal.entity

import com.google.gson.annotations.SerializedName

/**
 * Assets data
 * <p>
 * 资源数据
 *
 * @property name 文件名
 * @property contentType 文件类型
 * @property size 大小
 * @property downloadCount 下载次数
 * @property createdAt 创建时间
 * @property updatedAt 更新时间
 * @property browserDownloadUrl 下载链接
 * @constructor Create empty Assets data
 *
 * @author BingZi-233
 * @since 2.0.0
 */
data class AssetsData(
    @SerializedName("name")
    val name: String,
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("size")
    val size: Long,
    @SerializedName("download_count")
    val downloadCount: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("browser_download_url")
    val browserDownloadUrl: String,
)
