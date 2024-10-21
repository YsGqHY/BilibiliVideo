package online.bingzi.bilibili.video.internal.nms

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.wrappers.Pair
import online.bingzi.bilibili.video.internal.entity.HandEnum
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class NMSImpl : NMS() {
    private val protocolManager = ProtocolLibrary.getProtocolManager()
    private val packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT)
    override fun sendPlayerMap(player: Player, itemStack: ItemStack, hand: HandEnum) {
        packet.integers.write(0, player.entityId)
        packet.slotStackPairLists.write(0, listOf(Pair(hand.wrapper, itemStack)))
        protocolManager.sendServerPacket(player, packet)
    }

}