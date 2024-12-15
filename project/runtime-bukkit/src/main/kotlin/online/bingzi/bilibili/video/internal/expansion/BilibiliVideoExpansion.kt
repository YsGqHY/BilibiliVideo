package online.bingzi.bilibili.video.internal.expansion

import online.bingzi.bilibili.video.api.BilibiliVideoAPI
import org.bukkit.OfflinePlayer
import taboolib.platform.compat.PlaceholderExpansion

/**
 * Bilibili video expansion
 * <p>
 * PlaceholderAPI 扩展
 * 提供与 Bilibili 相关的变量支持
 *
 * @constructor Create empty Bilibili video expansion
 *
 * @author BingZi-233
 * @since 2.0.2
 */
class BilibiliVideoExpansion : PlaceholderExpansion {
    /**
     * 扩展标识符
     */
    override val identifier: String = "bilibilivideo"

    /**
     * 处理变量请求
     * 
     * @param player 玩家对象
     * @param args 变量参数
     * @return 变量值
     *
     * @author BingZi-233
     * @since 2.0.2
     */
    override fun onPlaceholderRequest(player: OfflinePlayer?, args: String): String {
        // 如果玩家为空则返回空字符串
        if (player == null) return ""
        
        // 获取玩家绑定的实体信息
        val bindEntity = BilibiliVideoAPI.getPlayerBindEntity(player.uniqueId)?: return ""
        
        return when {
            // 返回绑定的B站UID
            args.lowercase() == "bind_mid" -> bindEntity.bilibiliMid?.toString() ?: "未绑定"
            // 返回绑定的B站用户名
            args.lowercase() == "bind_name" -> bindEntity.bilibiliName ?: "未绑定"
            // 返回绑定的时间
            args.lowercase() == "bind_time" -> bindEntity.createTime?.toString() ?: "未绑定"
            // 返回已领取的奖励数量
            args.lowercase() == "receive_count" -> BilibiliVideoAPI.getPlayerReceiveEntityByPlayerUUID(player.uniqueId).size.toString()
            // 检查特定BV视频是否已领取
            args.lowercase().startsWith("receive_") -> {
                val bv = args.substring(8)
                if (BilibiliVideoAPI.checkPlayerReceiveEntityByMidAndBv(bindEntity.bilibiliMid!!, bv) ||
                    BilibiliVideoAPI.checkPlayerReceiveEntityByPlayerUUIDAndBv(player.uniqueId, bv)) {
                    "已领取"
                } else {
                    "未领取"
                }
            }
            // 未知参数返回空字符串
            else -> ""
        }
    }
} 