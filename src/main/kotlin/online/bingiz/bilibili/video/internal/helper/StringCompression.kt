package online.bingiz.bilibili.video.internal.helper

import online.bingiz.bilibili.video.internal.database.DatabaseType
import java.util.*
import java.util.zip.Deflater
import java.util.zip.Inflater

/**
 * 使用Deflater算法对字符串进行压缩，并返回压缩后的结果字符串
 * @param type 数据库类型，默认为DatabaseType.INSTANCEOF
 * @return 压缩后的结果字符串，如果数据库类型为SQLITE，则返回原始字符串
 */
fun String.compress(type: DatabaseType = DatabaseType.INSTANCE): String {
    if (type == DatabaseType.SQLITE) {
        return this
    }
    val input = this.toByteArray()
    val deflater = Deflater()
    deflater.setInput(input)
    deflater.finish()

    val buffer = ByteArray(input.size)
    val compressedSize = deflater.deflate(buffer)

    return Base64.getEncoder().encodeToString(buffer.copyOf(compressedSize))
}

/**
 * 对使用Deflater算法压缩后的字符串进行解压，并返回解压后的原始字符串
 * @param type 数据库类型，默认为DatabaseType.INSTANCEOF
 * @return 解压后的原始字符串，如果数据库类型为SQLITE，则返回原始压缩字符串
 */
fun String.decompress(type: DatabaseType = DatabaseType.INSTANCE): String {
    if (type == DatabaseType.SQLITE) {
        return this
    }
    val input = Base64.getDecoder().decode(this)
    val inflater = Inflater()
    inflater.setInput(input)

    val buffer = ByteArray(1024)
    val output = StringBuilder()

    while (!inflater.finished()) {
        val decompressedSize = inflater.inflate(buffer)
        output.append(String(buffer, 0, decompressedSize))
    }

    return output.toString()
}
