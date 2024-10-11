package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.api.BilibiliVideoAPI
import online.bingzi.bilibili.video.internal.config.VideoConfig
import online.bingzi.bilibili.video.internal.helper.ketherEval
import online.bingzi.bilibili.video.internal.helper.toFormatter
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggestPlayers
import taboolib.common.platform.function.getProxyPlayer
import taboolib.module.lang.sendWarn

/**
 * Command receive
 * <p>
 * 命令·领取
 *
 * @constructor Create empty Command receive
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object CommandReceive {
    /**
     * 执行子命令的定义。
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    val execute = subCommand {
        dynamic(comment = "bv") {
            execute<ProxyPlayer> { sender, context, _ ->
                // 从上下文中获取 bv 参数
                val bilibiliBv = context["bv"]
                // 获取与发送者唯一 ID 绑定的 Bilibili 视频实体
                BilibiliVideoAPI.getPlayerBindEntity(sender.uniqueId)?.let { bindEntity ->
                    // 检查该玩家是否已经领取过该 bv 对应的奖励
                    if (BilibiliVideoAPI.checkPlayerReceiveEntityByMidAndBv(bindEntity.bilibiliMid!!, bilibiliBv)) {
                        // 已经领取过了，发送警告信息
                        BilibiliVideoAPI.getPlayerReceiveEntityByMidAndBv(bindEntity.bilibiliMid!!, bilibiliBv)?.let {
                            sender.sendWarn(
                                "commandReceiveFailed",
                                bilibiliBv,
                                bindEntity.bilibiliName.toString(),
                                bindEntity.bilibiliMid.toString(),
                                it.createTime!!.toFormatter(),
                                it.playerName.toString(),
                                it.playerUUID.toString()
                            )
                        }
                    } else {
                        // 还没有领取过，设置领取实体并执行相关命令
                        BilibiliVideoAPI.setPlayerReceiveEntityByPlayerUUIDAndBv(sender.uniqueId, sender.name, bilibiliBv, bindEntity.bilibiliMid.toString())
                        VideoConfig.getVideo(bilibiliBv)!!.command.ketherEval(sender)
                    }
                } ?: sender.sendWarn("bindNotFound") // 绑定实体未找到，发送警告信息
            }
            dynamic(comment = "player") {
                // 提供玩家名称的建议
                suggestPlayers()
                execute<ProxyPlayer> { sender, context, _ ->
                    // 从上下文中获取 bv 和玩家名称
                    val bilibiliBv = context["bv"]
                    val playerName = context["player"]
                    // 获取指定名称的玩家
                    val player = getProxyPlayer(playerName) ?: let {
                        sender.sendWarn("playerNotFound", playerName) // 玩家未找到，发送警告信息
                        return@execute
                    }
                    // 获取与该玩家唯一 ID 绑定的 Bilibili 视频实体
                    BilibiliVideoAPI.getPlayerBindEntity(player.uniqueId)?.let { bindEntity ->
                        // 检查该玩家是否已经领取过该 bv 对应的奖励
                        if (BilibiliVideoAPI.checkPlayerReceiveEntityByMidAndBv(bindEntity.bilibiliMid!!, bilibiliBv)) {
                            // 已经领取过了，发送警告信息
                            BilibiliVideoAPI.getPlayerReceiveEntityByMidAndBv(bindEntity.bilibiliMid!!, bilibiliBv)?.let {
                                sender.sendWarn(
                                    "commandReceivePlayerFailed",
                                    bilibiliBv,
                                    bindEntity.bilibiliName.toString(),
                                    bindEntity.bilibiliMid.toString(),
                                    it.createTime!!.toFormatter(),
                                    it.playerName.toString(),
                                    it.playerUUID.toString(),
                                    playerName
                                )
                            }
                        } else {
                            // 还没有领取过，设置领取实体并执行相关命令
                            BilibiliVideoAPI.setPlayerReceiveEntityByPlayerUUIDAndBv(
                                player.uniqueId,
                                player.name,
                                bilibiliBv,
                                bindEntity.bilibiliMid.toString()
                            )
                            VideoConfig.getVideo(bilibiliBv)!!.command.ketherEval(sender)
                        }
                    } ?: sender.sendWarn("bindPlayerNotFound", playerName) // 绑定实体未找到，发送警告信息
                }
            }
        }
    }
}
