package online.bingzi.bilibili.video.internal.command

import com.comphenix.protocol.utility.MinecraftReflection
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.subCommand
import taboolib.module.lang.sendInfo

/**
 * Command map info
 * <p>
 * 命令·地图信息
 *
 * @constructor Create empty Command map info
 *
 * @since 2.0.4
 */
object CommandMapInfo {
    /**
     * 执行获取地图包数据结构的命令。
     *
     * @since 2.0.4
     */
    val execute = subCommand {
        dynamic(comment = "type") {
            suggestion<ProxyCommandSender> { _, _ -> listOf("PacketPlayOutMap", "MapItemStructure") }
            execute<ProxyCommandSender> { sender, context, _ ->
                val type = context.argument(0)
                try {
                    when (type) {
                        "PacketPlayOutMap" -> {
                            // 获取 PacketPlayOutMap 类
                            val packetPlayOutMapClass = MinecraftReflection.getMinecraftClass("PacketPlayOutMap")
                            // 获取类的字段信息
                            val fields = packetPlayOutMapClass.declaredFields.joinToString("\n") { it.toString() }
                            // 发送信息给命令发送者
                            sender.sendInfo("packetPlayOutMapStructure", fields)
                        }

                        "MapItemStructure" -> {
                            // 获取 MapItem 类
                            val mapItemClass = MinecraftReflection.getMinecraftClass("ItemMap")
                            // 获取类的字段信息
                            val fields = mapItemClass.declaredFields.joinToString("\n") { it.toString() }
                            // 发送信息给命令发送者
                            sender.sendInfo("mapItemStructure", fields)
                        }

                        else -> {
                            sender.sendInfo("invalidType", type)
                        }
                    }
                } catch (e: Exception) {
                    sender.sendInfo("packetPlayOutMapError", e.message ?: "Unknown error")
                }
            }
        }
    }
} 