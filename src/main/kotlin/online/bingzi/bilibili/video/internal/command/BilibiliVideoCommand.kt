package online.bingzi.bilibili.video.internal.command

import online.bingzi.bilibili.video.internal.config.RewardConfigManager
import online.bingzi.bilibili.video.internal.credential.QrLoginService
import online.bingzi.bilibili.video.internal.repository.BoundAccountRepository
import online.bingzi.bilibili.video.internal.service.BindingService
import online.bingzi.bilibili.video.internal.service.CredentialService
import online.bingzi.bilibili.video.internal.service.RewardKetherExecutor
import online.bingzi.bilibili.video.internal.service.RewardService
import online.bingzi.bilibili.video.internal.ui.VirtualItemSession
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.platform.util.submit
import java.util.UUID

/**
 * /bv 主命令。
 *
 * 当前实现的子命令：
 * - /bv help        查看帮助
 * - /bv qrcode      生成绑定用二维码地图
 * - /bv videoqr <bvid>  生成跳转指定视频页面的二维码地图
 * - /bv triple <bvid>  使用当前玩家绑定的 B 站账号检测指定稿件的三连状态
 * - /bv status      查看当前绑定状态与凭证信息
 * - /bv reward <bvid>  基于三连记录登记奖励
 * - /bv admin credential ... 管理/查看凭证
 *
 * 权限节点：
 * - bilibili.video.use          玩家基础命令（qrcode/status/triple/reward/videoqr）
 * - bilibili.video.admin        管理员命令
 */
@CommandHeader(name = "bv", aliases = ["bilibili"], permission = "bilibili.video.use", permissionDefault = PermissionDefault.TRUE)
object BilibiliVideoCommand {

    /**
     * BVID 正则：BV 开头，长度 10-13 的字母数字组合。
     * 兼容旧版 9 位与新版 12 位编号。
     */
    private val BVID_REGEX = Regex("^BV1[A-Za-z0-9]{8,12}$")

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    /**
     * 生成二维码地图，供玩家扫码绑定 B 站账号。
     */
    @CommandBody(permission = "bilibili.video.use", permissionDefault = PermissionDefault.TRUE)
    val qrcode = subCommand {
        execute<Player> { player, _, _ ->
            player.submit(async = true) {
                val result = QrLoginService.startLogin(player)
                if (!result.success || result.qrUrl == null) {
                    val message = result.message
                    player.submit(async = false) {
                        player.sendMessage("§c[BV] $message")
                    }
                    return@submit
                }
                val qrUrl = result.qrUrl
                player.submit(async = false) {
                    // 使用虚拟物品发送二维码地图到主手
                    VirtualItemSession.sendVirtualItem(player, qrUrl)
                    player.sendMessage("§a[BV] 已为你生成二维码，请使用手机扫码完成绑定。")
                }
            }
        }
    }

    /**
     * 生成视频页面跳转二维码地图，供玩家扫码打开 B 站视频。
     *
     * 参数：
     * - bvid：视频的 BVID，例如 BVxxxxxxxxx
     *
     * 生成的二维码内容固定为官方视频页 URL：
     *   https://www.bilibili.com/video/<bvid>
     *
     * 与 /bv qrcode 共用 VirtualItemSession 通道，二维码生成本身不需要网络请求，
     * 因此在主线程同步完成，避免与异步通道互相覆盖导致玩家无法及时看到二维码。
     */
    @CommandBody(permission = "bilibili.video.use", permissionDefault = PermissionDefault.TRUE)
    val videoqr = subCommand {
        dynamic("bvid") {
            suggestion<Player> { _, _ ->
                RewardConfigManager.getConfiguredBvids()
            }
            execute<Player> { player, context, _ ->
                val raw = context["bvid"].trim()
                val bvid = extractBvid(raw)
                if (bvid == null) {
                    player.sendMessage("§c[BV] 无法识别的 BVID：$raw，应为 BV 开头、长度 10-13 的字母数字组合。")
                    return@execute
                }
                val videoUrl = "https://www.bilibili.com/video/$bvid"
                VirtualItemSession.sendVirtualItem(player, videoUrl)
                player.sendMessage("§a[BV] 已生成视频跳转二维码，扫码即可打开：$videoUrl")
            }
        }
    }

    /**
     * 查看当前玩家的绑定状态与凭证信息。
     */
    @CommandBody(permission = "bilibili.video.use", permissionDefault = PermissionDefault.TRUE)
    val status = subCommand {
        execute<Player> { player, _, _ ->
            player.submit(async = true) {
                val binding = BindingService.getBoundAccount(player.uniqueId.toString())
                val credential = CredentialService.getCredentialInfo(player)

                player.submit(async = false) {
                    if (binding == null) {
                        player.sendMessage("§c[BV] 你还没有绑定任何 B 站账号。")
                    } else {
                        player.sendMessage("§b[BV] 当前绑定信息：")
                        player.sendMessage(" §7- 玩家：§f${binding.playerName}")
                        player.sendMessage(" §7- B 站 UID：§f${binding.bilibiliMid}")
                        player.sendMessage(" §7- B 站昵称：§f${binding.bilibiliName}")
                    }

                    if (credential == null) {
                        player.sendMessage("§e[BV] 尚未为你保存登录凭证，请通过 /bv qrcode 扫码登录。")
                    } else {
                        val statusText = when (credential.status) {
                            0 -> "§c禁用"
                            1 -> "§a正常"
                            2 -> "§c过期"
                            else -> "§e未知(${credential.status})"
                        }
                        player.sendMessage("§b[BV] 凭证信息：")
                        player.sendMessage(" §7- 标签：§f${credential.label}")
                        player.sendMessage(" §7- 状态：$statusText")
                        player.sendMessage(" §7- 绑定 UID：§f${credential.bilibiliMid ?: binding?.bilibiliMid}")
                    }
                }
            }
        }
    }

    /**
     * 使用当前玩家绑定的 B 站账号检测指定稿件的三连状态。
     *
     * 参数：
     * - bvid：视频的 BVID，例如 BVxxxxxxxxx
     */
    @CommandBody(permission = "bilibili.video.use", permissionDefault = PermissionDefault.TRUE)
    val triple = subCommand {
        dynamic("bvid") {
            suggestion<Player> { _, _ ->
                RewardConfigManager.getConfiguredBvids()
            }
            execute<Player> { player, context, _ ->
                val bvid = context["bvid"]
                if (!ensureConfiguredBvid(player, bvid)) {
                    return@execute
                }
                player.submit(async = true) {
                    val result = CredentialService.checkTripleByPlayer(player, bvid)
                    player.submit(async = false) {
                        if (!result.success || result.tripleStatus == null) {
                            player.sendMessage("§c[BV] ${result.message}")
                            return@submit
                        }
                        val status = result.tripleStatus
                        val likeText = if (status.liked) "§a已点赞" else "§c未点赞"
                        val coinText = if (status.coinCount > 0) "§a已投币(${status.coinCount})" else "§c未投币"
                        val favText = if (status.favoured) "§a已收藏" else "§c未收藏"
                        val tripleText = if (status.isTriple) "§a已完成三连" else "§e尚未完成三连"

                        player.sendMessage("§b[BV] 对稿件 $bvid 的三连状态：")
                        player.sendMessage(" §7- $likeText")
                        player.sendMessage(" §7- $coinText")
                        player.sendMessage(" §7- $favText")
                        player.sendMessage(" §7- $tripleText")
                    }
                }
            }
        }
    }

    /**
     * 基于三连记录登记奖励。
     */
    @CommandBody(permission = "bilibili.video.use", permissionDefault = PermissionDefault.TRUE)
    val reward = subCommand {
        dynamic("bvid") {
            suggestion<Player> { _, _ ->
                RewardConfigManager.getConfiguredBvids()
            }
            execute<Player> { player, context, _ ->
                val bvid = context["bvid"]
                if (!ensureConfiguredBvid(player, bvid)) {
                    return@execute
                }
                player.submit(async = true) {
                    val result = RewardService.rewardByPlayerAndBvid(player, bvid)
                    player.submit(async = false) {
                        if (!result.success) {
                            player.sendMessage("§c[BV] ${result.message}")
                            return@submit
                        }

                        // 成功时，若存在奖励模板则执行 Kether
                        val template = result.template
                        if (template != null && result.bvid != null) {
                            val targetKey = result.targetKey ?: result.bvid
                            RewardKetherExecutor.execute(
                                player = player,
                                template = template,
                                bvid = result.bvid,
                                targetKey = targetKey
                            )
                        }

                        player.sendMessage("§a[BV] ${result.message}")
                    }
                }
            }
        }
    }

    /**
     * 管理员命令：管理与查看凭证。
     *
     * - /bv admin credential list
     * - /bv admin credential info <label>
     * - /bv admin credential refresh <label>   （占位，刷新逻辑后续实现）
     * - /bv admin unbind <target>
     * - /bv admin reload
     */
    @CommandBody(permission = "bilibili.video.admin", permissionDefault = PermissionDefault.OP)
    val admin = subCommand {
        literal("unbind") {
            dynamic("target") {
                suggestion<ProxyCommandSender> { _, _ ->
                    Bukkit.getOnlinePlayers().map { it.name }
                }
                execute<ProxyCommandSender> { sender, context, _ ->
                    val targetArg = context["target"]
                    val target = resolveUnbindTarget(targetArg)
                    if (target == null) {
                        sender.sendMessage("§c[BV] 未找到与 $targetArg 匹配的玩家、UUID 或 B 站 UID。")
                        return@execute
                    }
                    val result = BindingService.unbind(target.playerUuid)
                    if (!result.success) {
                        sender.sendMessage("§c[BV] ${result.message}")
                        return@execute
                    }
                    val nameText = target.displayName ?: target.playerUuid
                    val midText = target.bilibiliMid?.toString() ?: "未知 UID"
                    sender.sendMessage("§a[BV] 已解除玩家 $nameText 与 B 站账号 $midText 的绑定。")
                }
            }
        }
        literal("credential") {
            literal("list") {
                execute<ProxyCommandSender> { sender, _, _ ->
                    val all = online.bingzi.bilibili.video.internal.repository.CredentialRepository.findAll()
                    if (all.isEmpty()) {
                        sender.sendMessage("§e[BV] 当前没有任何凭证记录。")
                        return@execute
                    }
                    sender.sendMessage("§b[BV] 凭证列表：")
                    all.forEach {
                        val statusText = when (it.status) {
                            0 -> "禁用"
                            1 -> "正常"
                            2 -> "过期"
                            else -> "未知(${it.status})"
                        }
                        sender.sendMessage(" §7- §f${it.label} §7| UID=${it.bilibiliMid ?: "?"} | 状态=$statusText")
                    }
                }
            }
            literal("info") {
                dynamic("label") {
                    execute<ProxyCommandSender> { sender, context, _ ->
                        val label = context["label"]
                        val info = CredentialService.getCredentialInfo(label)
                        if (info == null) {
                            sender.sendMessage("§c[BV] 未找到名为 $label 的凭证。")
                            return@execute
                        }
                        sender.sendMessage("§b[BV] 凭证详情：$label")
                        sender.sendMessage(" §7- UID：§f${info.bilibiliMid ?: "未知"}")
                        sender.sendMessage(" §7- 状态：§f${info.status}")
                        sender.sendMessage(" §7- lastUsedAt：§f${info.lastUsedAt ?: 0}")
                        sender.sendMessage(" §7- expiredAt：§f${info.expiredAt ?: 0}")
                    }
                }
            }
            literal("refresh") {
                dynamic("label") {
                    execute<ProxyCommandSender> { sender, context, _ ->
                        val label = context["label"]
                        // 刷新逻辑尚未实现，这里仅做占位与提示。
                        sender.sendMessage("§e[BV] 凭证刷新流程尚未实现：$label")
                    }
                }
            }
        }
        literal("reload") {
            execute<ProxyCommandSender> { sender, _, _ ->
                val ok = RewardConfigManager.reload()
                if (ok) {
                    val size = RewardConfigManager.getConfiguredBvids().size
                    sender.sendMessage("§a[BV] 已重载 config.yml，当前共登记 $size 个 reward.videos 项。")
                } else {
                    sender.sendMessage("§c[BV] 重载 config.yml 失败，请检查后台日志。")
                }
            }
        }
        createHelper()
    }

    private fun ensureConfiguredBvid(player: Player, bvid: String): Boolean {
        if (!RewardConfigManager.isBvidConfigured(bvid)) {
            player.sendMessage("§c[BV] 稿件 $bvid 尚未在 config.yml 的 reward.videos 中登记，无法使用该命令。")
            return false
        }
        return true
    }

    /**
     * 从玩家输入中提取 BVID，支持以下格式：
     * - BVxxxxxxxxxx            直接输入
     * - BV1xxxxxxxxxx           直接输入
     * - https://www.bilibili.com/video/BVxxxxxxxxxx
     * - https://www.bilibili.com/video/BVxxxxxxxxxx?p=1&spm=...
     * - https://b23.tv/xxxxxx   （无法定位，提示玩家用 BV 号）
     *
     * 仅在格式合法时返回，否则返回 null。
     */
    private fun extractBvid(raw: String): String? {
        if (raw.isEmpty()) return null
        // 1. 如果玩家直接粘贴了完整 URL，从中截取 BV 段
        val fromUrl = BVID_REGEX.find(raw)?.value
        if (fromUrl != null && BVID_REGEX.matches(fromUrl)) {
            return fromUrl
        }
        // 2. 否则按字面 BVID 校验
        if (BVID_REGEX.matches(raw)) {
            return raw
        }
        return null
    }

    private data class UnbindTarget(
        val playerUuid: String,
        val displayName: String?,
        val bilibiliMid: Long?
    )

    private fun resolveUnbindTarget(argument: String): UnbindTarget? {
        runCatching { UUID.fromString(argument) }.getOrNull()?.let { uuid ->
            val uuidStr = uuid.toString()
            val binding = BoundAccountRepository.findByPlayerUuid(uuidStr, includeInactive = true)
            val name = binding?.playerName ?: Bukkit.getOfflinePlayer(uuid).name
            return UnbindTarget(uuidStr, name, binding?.bilibiliMid)
        }

        Bukkit.getPlayerExact(argument)?.let { player ->
            val uuidStr = player.uniqueId.toString()
            val binding = BoundAccountRepository.findByPlayerUuid(uuidStr, includeInactive = true)
            return UnbindTarget(uuidStr, player.name, binding?.bilibiliMid)
        }

        BoundAccountRepository.findByPlayerName(argument, includeInactive = true)?.let { binding ->
            return UnbindTarget(binding.playerUuid, binding.playerName, binding.bilibiliMid)
        }

        argument.toLongOrNull()?.let { mid ->
            BoundAccountRepository.findByBilibiliMid(mid, includeInactive = true)?.let { binding ->
                return UnbindTarget(binding.playerUuid, binding.playerName, binding.bilibiliMid)
            }
        }

        return null
    }
}
