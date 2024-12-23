package online.bingzi.bilibili.video.internal.nms

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.wrappers.Pair
import online.bingzi.bilibili.video.internal.entity.HandEnum
import online.bingzi.bilibili.video.internal.map.ImageMapRenderer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.map.MapView
import taboolib.common.util.unsafeLazy
import taboolib.library.reflex.Reflex.Companion.getProperty
import taboolib.library.reflex.Reflex.Companion.invokeConstructor
import taboolib.library.reflex.Reflex.Companion.invokeMethod
import taboolib.library.reflex.Reflex.Companion.setProperty
import taboolib.library.reflex.Reflex.Companion.unsafeInstance
import taboolib.library.xseries.XMaterial
import taboolib.module.nms.MinecraftVersion
import taboolib.module.nms.nmsClass
import taboolib.module.nms.sendPacket
import taboolib.module.nms.sendPacketBlocking
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import taboolib.platform.util.modifyMeta
import java.awt.image.BufferedImage
import java.lang.reflect.Array
import java.util.*

@Suppress("DEPRECATION")
class NMSImpl : NMS() {
    private val protocolManager = ProtocolLibrary.getProtocolManager()
    private val packetEntityEquipment = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
    private val classPacketPlayOutMap = nmsClass("PacketPlayOutMap")
    private val classMapData: Class<*> by unsafeLazy { findClass("net.minecraft.world.level.saveddata.maps.WorldMap\$b", "net.minecraft.world.level.saveddata.maps.MapItemSavedData\$MapPatch") }
    private val classMapId: Class<*> by unsafeLazy { Class.forName("net.minecraft.world.level.saveddata.maps.MapId") }
    private val classMapIcon by unsafeLazy { nmsClass("MapIcon") }

    /**
     * 发送虚拟地图给玩家
     * 
     * @param player 目标玩家对象
     * @param bufferedImage 要发送的图像
     * @param hand 玩家手中的物品类型
     * @param itemBuilder 用于构建物品的lambda表达式
     *
     * @author BingZi-233
     * @since 2.0.0
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
     *
     * @author BingZi-233
     * @since 2.0.0
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
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun sendMapToPlayer(player: Player, bufferedImage: BufferedImage, hand: HandEnum, async: Boolean, itemBuilder: ItemBuilder.() -> Unit) {
        val mapView = createMapView(bufferedImage)
        val mapItem = createMapItem(mapView, itemBuilder)
        sendEquipmentPacket(player, hand, mapItem)
        sendMapPacket(player, mapView, mapItem, async)
    }

    /**
     * 创建地图视图
     * 
     * @param bufferedImage 要渲染的图像
     * @return 创建的地图视图
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun createMapView(bufferedImage: BufferedImage): MapView {
        val imageMapRenderer = ImageMapRenderer(bufferedImage)
        return Bukkit.createMap(Bukkit.getWorlds()[0]).apply {
            renderers.forEach { removeRenderer(it) }
            addRenderer(imageMapRenderer)
        }
    }

    /**
     * 创建地图物品
     * 
     * @param mapView 地图视图
     * @param itemBuilder 用于构建物品的lambda表达式
     * @return 创建的地图物品
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun createMapItem(mapView: MapView, itemBuilder: ItemBuilder.() -> Unit): ItemStack {
        return if (MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_13)) {
            buildItem(XMaterial.FILLED_MAP, itemBuilder).apply {
                modifyMeta<MapMeta> { this.mapView = mapView }
            }
        } else {
            buildItem(XMaterial.FILLED_MAP) {
                damage = mapView.invokeMethod<Short>("getId")!!.toInt()
                itemBuilder(this)
            }
        }
    }

    /**
     * 发送装备包给玩家
     * 
     * @param player 目标玩家对象
     * @param hand 玩家手中的物品类型
     * @param mapItem 地图物品
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun sendEquipmentPacket(player: Player, hand: HandEnum, mapItem: ItemStack) {
        packetEntityEquipment.integers.write(0, player.entityId)
        packetEntityEquipment.slotStackPairLists.write(0, listOf(Pair(hand.wrapper, mapItem)))
        protocolManager.sendServerPacket(player, packetEntityEquipment)
    }

    /**
     * 发送地图包给玩家
     * 
     * @param player 目标玩家对象
     * @param mapView 地图视图
     * @param mapItem 地图物品
     * @param async 是否异步发送
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun sendMapPacket(player: Player, mapView: MapView, mapItem: ItemStack, async: Boolean) {
        val buffer = mapView.invokeMethod<Any>("render", player)!!.getProperty<ByteArray>("buffer")
        val packetPlayOutMap = classPacketPlayOutMap.unsafeInstance()

        configureMapPacket(packetPlayOutMap, mapView, mapItem, buffer!!)

        if (async) {
            player.sendPacket(packetPlayOutMap)
        } else {
            player.sendPacketBlocking(packetPlayOutMap)
        }
    }

    /**
     * 配置地图包
     * 
     * @param packet 地图包对象
     * @param mapView 地图视图
     * @param mapItem 地图物品
     * @param buffer 地图数据缓冲区
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun configureMapPacket(packet: Any, mapView: MapView, mapItem: ItemStack, buffer: ByteArray) {
        when {
            MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_21) -> configureMapPacketV121(packet, mapView, buffer)
            MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_20) -> configureMapPacketV120(packet, mapView, buffer)
            MinecraftVersion.isUniversal -> configureMapPacketUniversal(packet, mapItem, mapView, buffer)
            MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_14) -> configureMapPacketV114(packet, mapItem, mapView, buffer)
            MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_12) -> configureMapPacketV112(packet, mapItem, mapView, buffer)
            else -> configureMapPacketLegacy(packet, mapView, buffer)
        }
    }

    /**
     * 配置1.21版本及以上的地图包
     * 
     * @param packet 地图包对象
     * @param mapView 地图视图
     * @param buffer 地图数据缓冲区
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun configureMapPacketV121(packet: Any, mapView: MapView, buffer: ByteArray) {
        packet.setProperty("mapId", classMapId.invokeConstructor(mapView.id))
        packet.setProperty("scale", mapView.scale.value)
        packet.setProperty("locked", false)
        packet.setProperty("decorations", Optional.empty<List<Any>>())
        packet.setProperty("colorPatch", Optional.of(createMapDataInstance(buffer)))
    }

    /**
     * 配置1.20版本的地图包
     * 
     * @param packet 地图包对象
     * @param mapView 地图视图
     * @param buffer 地图数据缓冲区
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun configureMapPacketV120(packet: Any, mapView: MapView, buffer: ByteArray) {
        packet.setProperty("mapId", mapView.id)
        packet.setProperty("scale", mapView.scale.value)
        packet.setProperty("locked", false)
        packet.setProperty("decorations", ArrayList<Any>())
        packet.setProperty("colorPatch", createMapDataInstance(buffer))
    }

    /**
     * 配置通用版本的地图包
     * 
     * @param packet 地图包对象
     * @param mapItem 地图物品
     * @param mapView 地图视图
     * @param buffer 地图数据缓冲区
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun configureMapPacketUniversal(packet: Any, mapItem: ItemStack, mapView: MapView, buffer: ByteArray) {
        packet.setProperty("mapId", (mapItem.itemMeta as MapMeta).mapId)
        packet.setProperty("scale", mapView.scale.value)
        packet.setProperty("locked", false)
        packet.setProperty("decorations", ArrayList<Any>())
        packet.setProperty("colorPatch", createMapDataInstance(buffer))
    }

    /**
     * 配置1.14版本的地图包
     * 
     * @param packet 地图包对象
     * @param mapItem 地图物品
     * @param mapView 地图视图
     * @param buffer 地图数据缓冲区
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun configureMapPacketV114(packet: Any, mapItem: ItemStack, mapView: MapView, buffer: ByteArray) {
        packet.setProperty("a", (mapItem.itemMeta as MapMeta).mapId)
        packet.setProperty("b", mapView.scale.value)
        packet.setProperty("c", false)
        packet.setProperty("d", false)
        packet.setProperty("e", Array.newInstance(classMapIcon, 0))
        packet.setProperty("f", 0)
        packet.setProperty("g", 0)
        packet.setProperty("h", 128)
        packet.setProperty("i", 128)
        packet.setProperty("j", buffer)
    }

    /**
     * 配置1.12版本的地图包
     * 
     * @param packet 地图包对象
     * @param mapItem 地图物品
     * @param mapView 地图视图
     * @param buffer 地图数据缓冲区
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun configureMapPacketV112(packet: Any, mapItem: ItemStack, mapView: MapView, buffer: ByteArray) {
        packet.setProperty("a", if (MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_13)) (mapItem.itemMeta as MapMeta).mapId else mapView.invokeMethod<Short>("getId")!!.toInt())
        packet.setProperty("b", mapView.scale.value)
        packet.setProperty("c", false)
        packet.setProperty("d", Array.newInstance(classMapIcon, 0))
        packet.setProperty("e", 0)
        packet.setProperty("f", 0)
        packet.setProperty("g", 128)
        packet.setProperty("h", 128)
        packet.setProperty("i", buffer)
    }

    /**
     * 配置旧版本的地图包
     * 
     * @param packet 地图包对象
     * @param mapView 地图视图
     * @param buffer 地图数据缓冲区
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun configureMapPacketLegacy(packet: Any, mapView: MapView, buffer: ByteArray) {
        packet.setProperty("a", mapView.id)
        packet.setProperty("b", mapView.scale.value)
        packet.setProperty("c", Array.newInstance(classMapIcon, 0))
        packet.setProperty("d", 0)
        packet.setProperty("e", 0)
        packet.setProperty("f", 128)
        packet.setProperty("g", 128)
        packet.setProperty("h", buffer)
    }

    /**
     * 创建地图数据实例
     * 
     * @param buffer 地图数据缓冲区
     * @return 地图数据实例
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun createMapDataInstance(buffer: ByteArray): Any {
        return classMapData.unsafeInstance().apply {
            setProperty("startX", 0)
            setProperty("startY", 0)
            setProperty("width", 128)
            setProperty("height", 128)
            setProperty("mapColors", buffer)
        }
    }

    /**
     * 查找类
     * 
     * @param classNames 类名数组
     * @return 找到的类
     * @throws ClassNotFoundException 如果没有找到任何指定的类
     *
     * @author BingZi-233
     * @since 2.0.4
     */
    private fun findClass(vararg classNames: String): Class<*> {
        for (className in classNames) {
            try {
                return Class.forName(className)
            } catch (e: ClassNotFoundException) {
                // 忽略并尝试下一个类名
            }
        }
        throw ClassNotFoundException("无法找到任何指定的类: ${classNames.joinToString()}")
    }
}
