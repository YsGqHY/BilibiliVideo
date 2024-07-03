package online.bingzi.bilibili.video.internal.helper

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.awt.Color
import java.awt.image.BufferedImage


/**
 * To buffered image
 * 将URL转换为BufferedImage
 *
 * @param size
 * @return
 */
fun String.toBufferedImage(size: Int): BufferedImage {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(this, BarcodeFormat.QR_CODE, size, size)
    val image = BufferedImage(size, size, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()
    graphics.color = Color.WHITE
    graphics.fillRect(0, 0, size, size)
    graphics.color = Color.BLACK
    for (x in 0 until size) {
        for (y in 0 until size) {
            if (bitMatrix.get(x, y)) {
                graphics.fillRect(x, y, 1, 1)
            }
        }
    }
    graphics.dispose()
    return image
}

/**
 * Send virtual item
 * 发送虚拟物品给调用玩家
 *
 * @param itemStack
 * @param slot
 */
fun Player.sendVirtualItem(itemStack: ItemStack, slot: Int) {
    // 创建一个装载物品数据的包
    val packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SET_SLOT)

    // 设置包中的数据
    packet.integers.write(0, 0)  // 窗口ID，0表示主背包
    packet.integers.write(1, slot)  // 插槽ID，指定要放置物品的插槽编号

    // 将物品堆栈放入包中
    packet.itemModifier.write(0, itemStack)

    try {
        // 发送包给指定的玩家
        ProtocolLibrary.getProtocolManager().sendServerPacket(this, packet)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
