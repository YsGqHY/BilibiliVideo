package online.bingzi.bilibili.video.internal.helper

import java.text.SimpleDateFormat
import java.util.*

/**
 * Date time formatter
 * <p>
 * 时间格式化
 */
private val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

/**
 * To formatter
 * <p>
 * 时间格式化
 *
 * @return
 *
 * @author BingZi-233
 * @since 2.0.0
 */
fun Date.toFormatter(): String {
    return dateTimeFormatter.format(this)
}
