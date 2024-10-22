package online.bingzi.bilibili.video.internal.nms

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.wrappers.Pair
import net.minecraft.network.protocol.game.PacketPlayOutMap
import net.minecraft.world.level.saveddata.maps.WorldMap
import online.bingzi.bilibili.video.internal.entity.HandEnum
import online.bingzi.bilibili.video.internal.map.ImageMapRenderer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.MapMeta
import taboolib.library.reflex.Reflex.Companion.getProperty
import taboolib.library.reflex.Reflex.Companion.invokeMethod
import taboolib.library.reflex.Reflex.Companion.setProperty
import taboolib.library.reflex.Reflex.Companion.unsafeInstance
import taboolib.library.xseries.XMaterial
import taboolib.module.nms.MinecraftVersion
import taboolib.module.nms.NMSMap.Companion.classMapData
import taboolib.module.nms.NMSMap.Companion.classMapIcon
import taboolib.module.nms.nmsClass
import taboolib.module.nms.sendPacketBlocking
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import java.awt.image.BufferedImage
import java.lang.reflect.Array

class NMSImpl : NMS() {
    private val protocolManager = ProtocolLibrary.getProtocolManager()
    private val packetEntityEquipment = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
    private val classPacketPlayOutMap = nmsClass("PacketPlayOutMap")
    override fun sendPlayerMap(player: Player, bufferedImage: BufferedImage, hand: HandEnum, itemBuilder: ItemBuilder.() -> Unit) {
        val imageMapRenderer = ImageMapRenderer(bufferedImage)
        val itemStack = buildItem(XMaterial.FILLED_MAP, itemBuilder)
        val mapView = Bukkit.createMap(Bukkit.getWorlds()[0]).apply {
            renderers.forEach { removeRenderer(it) }
        }
        mapView.addRenderer(imageMapRenderer)
        itemStack.itemMeta?.let {
            if (it is MapMeta) {
                it.mapView = mapView
                itemStack.itemMeta = it
            }
        }
        packetEntityEquipment.integers.write(0, player.entityId)
        packetEntityEquipment.slotStackPairLists.write(0, listOf(Pair(hand.wrapper, itemStack)))
        protocolManager.sendServerPacket(player, packetEntityEquipment)

        val buffer = mapView.invokeMethod<Any>("render", player)!!.getProperty<ByteArray>("buffer")
        var packetPlayOutMap = classPacketPlayOutMap.unsafeInstance()
        when {
            // 如果高于 1.20 版本，则使用新的方法发送地图渲染包
            MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_20) -> {
                packetPlayOutMap = PacketPlayOutMap(mapView.id, 0, false, listOf(), WorldMap.b(0, 0, 128, 128, buffer))
            }
            // 1.17+
            MinecraftVersion.isUniversal -> {
                packetPlayOutMap.setProperty("mapId", (itemStack.itemMeta as MapMeta).mapId)
                packetPlayOutMap.setProperty("scale", mapView.scale.value)
                packetPlayOutMap.setProperty("locked", false)
                packetPlayOutMap.setProperty("decorations", ArrayList<Any>())
                packetPlayOutMap.setProperty("colorPatch", classMapData.unsafeInstance().also {
                    it.setProperty("startX", 0)
                    it.setProperty("startY", 0)
                    it.setProperty("width", 128)
                    it.setProperty("height", 128)
                    it.setProperty("mapColors", buffer)
                })
            }
            // 1.14+
            MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_14) -> {
                packetPlayOutMap.setProperty("a", (itemStack.itemMeta as MapMeta).mapId)
                packetPlayOutMap.setProperty("b", mapView.scale.value)
                packetPlayOutMap.setProperty("c", false)
                packetPlayOutMap.setProperty("d", false)
                packetPlayOutMap.setProperty("e", Array.newInstance(classMapIcon, 0))
                packetPlayOutMap.setProperty("f", 0)
                packetPlayOutMap.setProperty("g", 0)
                packetPlayOutMap.setProperty("h", 128)
                packetPlayOutMap.setProperty("i", 128)
                packetPlayOutMap.setProperty("j", buffer)
            }
            // 1.12+
            MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_12) -> {
                if (MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_13)) {
                    packetPlayOutMap.setProperty("a", (itemStack.itemMeta as MapMeta).mapId)
                } else {
                    packetPlayOutMap.setProperty("a", mapView.invokeMethod<Short>("getId")!!.toInt())
                }
                packetPlayOutMap.setProperty("b", mapView.scale.value)
                packetPlayOutMap.setProperty("c", false)
                packetPlayOutMap.setProperty("d", Array.newInstance(classMapIcon, 0))
                packetPlayOutMap.setProperty("e", 0)
                packetPlayOutMap.setProperty("f", 0)
                packetPlayOutMap.setProperty("g", 128)
                packetPlayOutMap.setProperty("h", 128)
                packetPlayOutMap.setProperty("i", buffer)
            }
            // 1.12-
            else -> {
                packetPlayOutMap.setProperty("a", mapView.id)
                packetPlayOutMap.setProperty("b", mapView.scale.value)
                packetPlayOutMap.setProperty("c", Array.newInstance(classMapIcon, 0))
                packetPlayOutMap.setProperty("d", 0)
                packetPlayOutMap.setProperty("e", 0)
                packetPlayOutMap.setProperty("f", 128)
                packetPlayOutMap.setProperty("g", 128)
                packetPlayOutMap.setProperty("h", buffer)
            }
        }
        player.sendPacketBlocking(packetPlayOutMap)
    }
}
