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
import taboolib.library.xseries.XMaterial
import taboolib.module.nms.sendPacket
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import java.awt.image.BufferedImage

class NMSImpl : NMS() {
    private val protocolManager = ProtocolLibrary.getProtocolManager()
    private val packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
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
        packet.integers.write(0, player.entityId)
        packet.slotStackPairLists.write(0, listOf(Pair(hand.wrapper, itemStack)))
        protocolManager.sendServerPacket(player, packet)
        val buffer = mapView.invokeMethod<Any>("render", player)!!.getProperty<ByteArray>("buffer")
        val packetPlayOutMap = PacketPlayOutMap(mapView.id, 0, true, listOf(), WorldMap.b(0, 0, 128, 128, buffer))
        player.sendPacket(packetPlayOutMap)
    }
}
