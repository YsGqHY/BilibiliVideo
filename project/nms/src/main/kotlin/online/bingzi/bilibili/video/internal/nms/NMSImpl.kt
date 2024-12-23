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
     * 发送虚拟地图给玩家
     *
     * @param player 目标玩家对象
     * @param bufferedImage 要发送的图像
     * @param hand 玩家手中的物品类型
     * @param itemBuilder 用于构建物品的lambda表达式
     */
    override fun sendVirtualMapToPlayer(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit) {
        sendMapToPlayer(player, bufferedImage, hand, false, itemBuilder)
    }

    /**
     * 异步发送虚拟地图给玩家
     *
     * @param player 目标玩家对象
     * @param bufferedImage 要发送的图像
     * @param hand 玩家手中的物品类型
     * @param itemBuilder 用于构建物品的lambda表达式
     */
    override fun sendVirtualMapToPlayerAsync(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit) {
        sendMapToPlayer(player, bufferedImage, hand, true, itemBuilder)
    }

    /**
     * 发送地图到玩家
     *
     * @param player 目标玩家对象
     * @param bufferedImage 要发送的图像
     * @param hand 玩家手中的物品类型
     * @param async 是否异步发送
     * @param itemBuilder 用于构建物品的lambda表达式
     */
    private fun sendMapToPlayer(player: Player, bufferedImage: BufferedImage, hand: HandEnum, async: Boolean, itemBuilder: ItemBuilder.() -> Unit) {
        val mapView = createMapView(bufferedImage) // 创建地图视图
        val mapItem = createMapItem(itemBuilder) // 创建地图物品

        // 发送装备数据包来更新玩家的主手或副手
        sendEquipmentPacket(player, mapItem, hand)

        sendMapPacket(player, mapView, mapItem, async) // 发送地图数据包
    }

    /**
     * 创建地图视图
     *
     * @param bufferedImage 要渲染的图像
     * @return 创建的地图视图
     */
    private fun createMapView(bufferedImage: BufferedImage): MapView {
        val imageMapRenderer = ImageMapRenderer(bufferedImage) // 创建图像渲染器
        return Bukkit.createMap(Bukkit.getWorlds()[0]).apply {
            renderers.forEach { removeRenderer(it) } // 移除现有渲染器
            addRenderer(imageMapRenderer) // 添加新的图像渲染器
        }
    }

    /**
     * 创建地图物品
     *
     * @param itemBuilder 用于构建物品的lambda表达式
     * @return 创建的地图物品
     */
    private fun createMapItem(itemBuilder: ItemBuilder.() -> Unit): ItemStack {
        return ItemBuilder(XMaterial.FILLED_MAP).apply(itemBuilder).build() // 使用 ItemBuilder 构建物品
    }

    /**
     * 发送装备数据包
     *
     * @param player 目标玩家对象
     * @param mapItem 地图物品
     * @param hand 玩家手中的物品类型
     */
    private fun sendEquipmentPacket(player: Player, mapItem: ItemStack, hand: HandEnum) {
        val packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
        packet.integers.write(0, player.entityId) // 设置玩家实体ID

        val equipmentSlot = if (hand == HandEnum.MAIN_HAND) {
            EnumWrappers.ItemSlot.MAINHAND
        } else {
            EnumWrappers.ItemSlot.OFFHAND
        }

        packet.itemSlots.write(0, equipmentSlot) // 设置装备槽位
        packet.itemModifier.write(0, mapItem) // 设置物品

        protocolManager.sendServerPacket(player, packet) // 发送数据包
    }

    /**
     * 发送地图数据包
     *
     * @param player 目标玩家对象
     * @param mapView 地图视图
     * @param mapItem 地图物品
     * @param async 是否异步发送
     */
    private fun sendMapPacket(player: Player, mapView: MapView, mapItem: ItemStack, async: Boolean) {
        // 假设 ImageMapRenderer 有一个方法 getBufferForPlayer
        val imageMapRenderer = mapView.renderers.firstOrNull { it is ImageMapRenderer } as? ImageMapRenderer
        val buffer = imageMapRenderer?.getBufferForPlayer(player) ?: ByteArray(0) // 获取地图数据缓冲区
        val packet = protocolManager.createPacket(PacketType.Play.Server.MAP) // 创建地图数据包

        configureMapPacket(packet, mapView, buffer) // 配置地图数据包

        if (async) {
            protocolManager.sendServerPacket(player, packet) // 异步发送数据包
        } else {
            protocolManager.sendServerPacket(player, packet, true) // 同步发送数据包
        }
    }

    /**
     * 配置地图数据包
     *
     * @param packet 地图数据包
     * @param mapView 地图视图
     * @param buffer 地图数据缓冲区
     */
    private fun configureMapPacket(packet: PacketContainer, mapView: MapView, buffer: ByteArray) {
        packet.integers.write(0, mapView.id) // 设置地图ID
        packet.bytes.write(0, mapView.scale.value.toByte()) // 设置地图缩放比例
        packet.booleans.write(0, false) // 设置地图是否锁定
        packet.byteArrays.write(0, buffer) // 设置地图数据
    }
}
