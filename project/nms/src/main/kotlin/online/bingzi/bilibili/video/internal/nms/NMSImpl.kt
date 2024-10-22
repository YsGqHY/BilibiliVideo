package online.bingzi.bilibili.video.internal.nms

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.wrappers.Pair
import online.bingzi.bilibili.video.internal.entity.HandEnum
import online.bingzi.bilibili.video.internal.map.ImageMapRenderer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.MapMeta
import taboolib.library.xseries.XMaterial
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
        mapView.isVirtual
        itemStack.itemMeta?.let {
            if (it is MapMeta) {
                it.mapView = mapView
                itemStack.itemMeta = it
            }
        }
        packet.integers.write(0, player.entityId)
        packet.slotStackPairLists.write(0, listOf(Pair(hand.wrapper, itemStack)))
        protocolManager.sendServerPacket(player, packet)
    }

}
