package online.bingzi.bilibili.video.internal.nms

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import online.bingzi.bilibili.video.internal.entity.HandEnum
import online.bingzi.bilibili.video.internal.map.ImageMapRenderer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.map.MapView
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.ItemBuilder
import java.awt.image.BufferedImage

@Suppress("DEPRECATION")
class NMSImpl : NMS() {

    // 获取 ProtocolLib 的协议管理器实例
    private val protocolManager = ProtocolLibrary.getProtocolManager()

    /**
     * 获取地图ID的方法
     * 兼容不同版本的Bukkit API
     */
    private fun getMapId(mapView: MapView): Int {
        return try {
            // 1.12及以下版本使用getId()
            mapView.javaClass.getMethod("getId").invoke(mapView) as Int
        } catch (e: Exception) {
            try {
                // 1.13及以上版本使用id属性
                mapView.javaClass.getMethod("getMapId").invoke(mapView) as Int
            } catch (e: Exception) {
                try {
                    // 某些版本可能使用id字段
                    val field = mapView.javaClass.getDeclaredField("id")
                    field.isAccessible = true
                    field.get(mapView) as Int
                } catch (e: Exception) {
                    // 如果都失败了，使用默认值0
                    0
                }
            }
        }
    }

    /**
     * 发送虚拟地图给玩家
     */
    override fun sendVirtualMapToPlayer(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit) {
        sendMapToPlayer(player, bufferedImage, hand, false, itemBuilder)
    }

    /**
     * 异步发送虚拟地图给玩家
     */
    override fun sendVirtualMapToPlayerAsync(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit) {
        sendMapToPlayer(player, bufferedImage, hand, true, itemBuilder)
    }

    /**
     * 发送虚拟地图到玩家
     */
    private fun sendMapToPlayer(player: Player, bufferedImage: BufferedImage, hand: HandEnum, async: Boolean, itemBuilder: ItemBuilder.() -> Unit) {
        // 创建地图图并设置渲染器
        val mapView = createMapView(bufferedImage)
        
        // 获取渲染器
        val imageMapRenderer = mapView.renderers.filterIsInstance<ImageMapRenderer>().firstOrNull()
        
        // 创建地图物品
        val virtualMapItem = ItemBuilder(XMaterial.FILLED_MAP).apply {
            // 设置地图ID
            damage = getMapId(mapView)
            // 应用自定义设置
            itemBuilder()
        }.build()
        
        // 发送装备数据包
        val equipmentPacket = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
        equipmentPacket.integers.write(0, player.entityId)
        equipmentPacket.itemSlots.write(0, if (hand == HandEnum.MAIN_HAND) EnumWrappers.ItemSlot.MAINHAND else EnumWrappers.ItemSlot.OFFHAND)
        equipmentPacket.itemModifier.write(0, virtualMapItem)
        
        // 发送地图数据包
        val mapPacket = protocolManager.createPacket(PacketType.Play.Server.MAP)
        val buffer = imageMapRenderer?.getBufferForPlayer(player) ?: ByteArray(128 * 128)
        
        configureMapPacket(mapPacket, mapView, buffer)
        
        // 按顺序发送数据包
        if (async) {
            protocolManager.sendServerPacket(player, equipmentPacket)
            protocolManager.sendServerPacket(player, mapPacket)
        } else {
            protocolManager.sendServerPacket(player, equipmentPacket, false)
            protocolManager.sendServerPacket(player, mapPacket, false)
        }
    }

    /**
     * 创建地图视图
     */
    private fun createMapView(bufferedImage: BufferedImage): MapView {
        return Bukkit.createMap(Bukkit.getWorlds()[0]).apply {
            // 清除所有现有渲染器
            renderers.forEach { removeRenderer(it) }
            // 添加新的图像渲染器
            addRenderer(ImageMapRenderer(bufferedImage))
            // 设置地图属性
            scale = MapView.Scale.CLOSEST
            try {
                // 尝试设置追踪属性（新版本）
                javaClass.getMethod("setTrackingPosition", Boolean::class.java)?.invoke(this, false)
                javaClass.getMethod("setUnlimitedTracking", Boolean::class.java)?.invoke(this, false)
                javaClass.getMethod("setLocked", Boolean::class.java)?.invoke(this, true)
            } catch (e: Exception) {
                try {
                    // 尝试设置追踪属性（旧版本）
                    javaClass.getMethod("setTrackingPlayer", Boolean::class.java)?.invoke(this, false)
                } catch (e: Exception) {
                    // 忽略错误，继续执行
                }
            }
        }
    }

    /**
     * 配置地图数据包
     */
    private fun configureMapPacket(packet: PacketContainer, mapView: MapView, buffer: ByteArray) {
        val mapId = getMapId(mapView)
        packet.integers.write(0, mapId) // 设置地图ID
        packet.bytes.write(0, 0) // 设置地图缩放级别
        packet.booleans.write(0, false) // 设置是否追踪玩家
        packet.integers.write(1, 0) // X 偏移
        packet.integers.write(2, 0) // Y 偏移
        packet.integers.write(3, 128) // 宽度
        packet.integers.write(4, 128) // 高度
        packet.byteArrays.write(0, buffer) // 设置地图数据
    }
}
