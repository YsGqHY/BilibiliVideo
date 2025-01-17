package online.bingzi.bilibili.video.internal.helper

import online.bingzi.bilibili.video.internal.config.SettingConfig
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.MapMeta
import org.bukkit.map.MapCanvas
import org.bukkit.map.MapRenderer
import org.bukkit.map.MapView
import taboolib.common.platform.function.submit
import taboolib.common.util.unsafeLazy
import taboolib.library.reflex.Reflex.Companion.getProperty
import taboolib.library.reflex.Reflex.Companion.invokeConstructor
import taboolib.library.reflex.Reflex.Companion.invokeMethod
import taboolib.library.reflex.Reflex.Companion.setProperty
import taboolib.library.reflex.Reflex.Companion.unsafeInstance
import taboolib.library.xseries.XMaterial
import taboolib.module.nms.*
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem
import taboolib.platform.util.modifyMeta
import java.awt.image.BufferedImage
import java.io.File
import java.lang.reflect.Array
import java.net.URL
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.imageio.ImageIO

/**
 * 创建地图画（堵塞）
 *
 * @param url 图像地址
 * @param width 图像宽度
 * @param height 图像高度
 */
fun buildMap(
    url: String,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
): NMSMap {
    return NMSMap(URL(url).openStream().use { ImageIO.read(it) }.zoomed(width, height), hand, builder)
}

/**
 * 创建地图画（异步）
 *
 * @param url 图像地址
 * @param width 图像宽度
 * @param height 图像高度
 */
fun buildMap(
    url: URL,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
): CompletableFuture<NMSMap> {
    return CompletableFuture.supplyAsync {
        NMSMap(url.openStream().use { ImageIO.read(it) }.zoomed(width, height), hand, builder)
    }
}

/**
 * 创建地图画（堵塞）
 *
 * @param file 图像文件
 * @param width 图像宽度
 * @param height 图像高度
 */
fun buildMap(
    file: File,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
): NMSMap {
    return NMSMap(ImageIO.read(file).zoomed(width, height), hand, builder)
}

/**
 * 创建地图画（堵塞）
 *
 * @param image 图像对象
 * @param width 图像宽度
 * @param height 图像高度
 */
fun buildMap(
    image: BufferedImage,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
): NMSMap {
    return NMSMap(image.zoomed(width, height), hand, builder)
}

/**
 * 打开地图画（异步）
 *
 * @param url 图像地址
 * @param width 图像宽度
 * @param height 图像高度
 */
fun Player.sendMap(
    url: String,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
) {
    buildMap(URL(url), hand, width, height, builder).thenAccept { it.sendTo(this) }
}

/**
 * 打开地图画（异步）
 *
 * @param url 图像地址
 * @param width 图像宽度
 * @param height 图像高度
 */
fun Player.sendMap(
    url: URL,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
) {
    buildMap(url, hand, width, height, builder).thenAccept { it.sendTo(this) }
}

/**
 * 打开地图画（异步）
 *
 * @param file 图像文件
 * @param width 图像宽度
 * @param height 图像高度
 */
fun Player.sendMap(
    file: File,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
) {
    buildMap(file, hand, width, height, builder).sendTo(this)
}

/**
 * 打开地图画（异步）
 *
 * @param image 图像对象
 * @param width 图像宽度
 * @param height 图像高度
 */
fun Player.sendMap(
    image: BufferedImage,
    hand: NMSMap.Hand = NMSMap.Hand.MAIN,
    width: Int = 128,
    height: Int = 128,
    builder: ItemBuilder.() -> Unit = {}
) {
    buildMap(image, hand, width, height, builder).sendTo(this)
}

/**
 * 调整图片分辨率
 * 地图最佳显示分辨率为128*128
 */
fun BufferedImage.zoomed(width: Int = 128, height: Int = 128): BufferedImage {
    val tag = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    tag.graphics.drawImage(this, 0, 0, width, height, null)
    return tag
}

/**
 * 地图发包工具
 * 支持 1.8 - 1.17.1
 * @author xbaimiao, sky
 */
class NMSMap(val image: BufferedImage, var hand: Hand = Hand.MAIN, val builder: ItemBuilder.() -> Unit = {}) {

    enum class Hand {

        MAIN, OFF
    }

    companion object {

        val classPacketPlayOutSetSlot = nmsClass("PacketPlayOutSetSlot")
        val classPacketPlayOutMap = nmsClass("PacketPlayOutMap")
        val classCraftItemStack = obcClass("inventory.CraftItemStack")
        val classMapIcon by unsafeLazy { nmsClass("MapIcon") }

        /**
         * 鸣谢 [YsGqHY](https://github.com/YsGqHY) 提供的包寻找方法
         */
        val classMapData: Class<*> by unsafeLazy {
            try {
                // 尝试找Spigot的WorldMap.b
                val worldMap = if (MinecraftVersion.isEqual(MinecraftVersion.V1_21)) "c" else "b"
                Class.forName("net.minecraft.world.level.saveddata.maps.WorldMap\$$worldMap")
            } catch (e: ClassNotFoundException) {
                // 没有找到Spigot的WorldMap.b，尝试找Paper的MapItemSavedData.MapPatch
                Class.forName("net.minecraft.world.level.saveddata.maps.MapItemSavedData\$MapPatch")
            }
        }

        // 高版本把MapId改成了，这个东西主要是对1.20+的兼容
        val classMapId: Class<*> by unsafeLazy { Class.forName("net.minecraft.world.level.saveddata.maps.MapId") }
    }

    val mapRenderer = object : MapRenderer() {

        var rendered = false

        override fun render(mapView: MapView, mapCanvas: MapCanvas, player: Player) {
            if (rendered) {
                return
            }
            mapCanvas.drawImage(0, 0, image)
            rendered = true
        }
    }

    val mapView by unsafeLazy {
        val mapView = Bukkit.createMap(Bukkit.getWorlds()[0])
        mapView.addRenderer(mapRenderer)
        mapView
    }

    val mapItem by unsafeLazy {
        val map = if (MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_13)) {
            buildItem(XMaterial.FILLED_MAP, builder)
        } else {
            buildItem(XMaterial.FILLED_MAP) {
                damage = mapView.invokeMethod<Short>("getId")!!.toInt()
                builder(this)
            }
        }
        if (MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_13)) {
            map.modifyMeta<MapMeta> { mapView = this@NMSMap.mapView }
        } else {
            map
        }
    }

    fun sendTo(player: Player) {
        submit(delay = 3) {
            val container = if (MinecraftVersion.isUniversal) {
                player.getProperty<Any>("entity/inventoryMenu")
            } else {
                player.getProperty<Any>("entity/defaultContainer")
            }!!
            val windowsId = if (MinecraftVersion.isUniversal) {
                container.getProperty<Int>("containerId")
            } else {
                container.getProperty<Int>("windowId")
            }!!
            val nmsItem = classCraftItemStack.invokeMethod<Any>("asNMSCopy", mapItem, isStatic = true)
            val itemPacket = classPacketPlayOutSetSlot.unsafeInstance().also {
                if (MinecraftVersion.isUniversal) {
                    it.setProperty("containerId", windowsId)
                    it.setProperty("stateId", 1)
                    it.setProperty("slot", getMainHandSlot(player))
                    it.setProperty("itemStack", nmsItem)
                } else {
                    it.setProperty("a", windowsId)
                    it.setProperty("b", getMainHandSlot(player))
                    it.setProperty("c", nmsItem)
                }
            }
            if (SettingConfig.sendMapAsync) {
                player.sendPacket(itemPacket)
            } else {
                player.sendPacketBlocking(itemPacket)
            }
            val buffer = mapView.invokeMethod<Any>("render", player)!!.getProperty<ByteArray>("buffer")
            val packet = classPacketPlayOutMap.unsafeInstance()
            when {
                // 1.21+
                MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_21) -> {
                    // 1.21+这里被改成了MapId对象
                    packet.setProperty("mapId", classMapId.invokeConstructor(mapView.id))
                    packet.setProperty("scale", mapView.scale.value)
                    packet.setProperty("locked", false)
                    // 1.21+这里被Optional包裹了
                    packet.setProperty("decorations", Optional.empty<List<Any>>())
                    packet.setProperty("colorPatch", Optional.of(classMapData.unsafeInstance().also {
                        it.setProperty("startX", 0)
                        it.setProperty("startY", 0)
                        it.setProperty("width", 128)
                        it.setProperty("height", 128)
                        it.setProperty("mapColors", buffer)
                    }))
                }
                // 1.20+
                MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_20) -> {
                    packet.setProperty("mapId", mapView.id)
                    packet.setProperty("scale", mapView.scale.value)
                    packet.setProperty("locked", false)
                    packet.setProperty("decorations", ArrayList<Any>())
                    packet.setProperty("colorPatch", classMapData.unsafeInstance().also {
                        it.setProperty("startX", 0)
                        it.setProperty("startY", 0)
                        it.setProperty("width", 128)
                        it.setProperty("height", 128)
                        it.setProperty("mapColors", buffer)
                    })
                }
                // 1.17+
                MinecraftVersion.isUniversal -> {
                    packet.setProperty("mapId", (mapItem.itemMeta as MapMeta).mapId)
                    packet.setProperty("scale", mapView.scale.value)
                    packet.setProperty("locked", false)
                    packet.setProperty("decorations", ArrayList<Any>())
                    packet.setProperty("colorPatch", classMapData.unsafeInstance().also {
                        it.setProperty("startX", 0)
                        it.setProperty("startY", 0)
                        it.setProperty("width", 128)
                        it.setProperty("height", 128)
                        it.setProperty("mapColors", buffer)
                    })
                }
                // 1.14+
                MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_14) -> {
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
                // 1.12+
                MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_12) -> {
                    if (MinecraftVersion.isHigherOrEqual(MinecraftVersion.V1_13)) {
                        packet.setProperty("a", (mapItem.itemMeta as MapMeta).mapId)
                    } else {
                        packet.setProperty("a", mapView.invokeMethod<Short>("getId")!!.toInt())
                    }
                    packet.setProperty("b", mapView.scale.value)
                    packet.setProperty("c", false)
                    packet.setProperty("d", Array.newInstance(classMapIcon, 0))
                    packet.setProperty("e", 0)
                    packet.setProperty("f", 0)
                    packet.setProperty("g", 128)
                    packet.setProperty("h", 128)
                    packet.setProperty("i", buffer)
                }
                // 1.12-
                else -> {
                    packet.setProperty("a", mapView.id)
                    packet.setProperty("b", mapView.scale.value)
                    packet.setProperty("c", Array.newInstance(classMapIcon, 0))
                    packet.setProperty("d", 0)
                    packet.setProperty("e", 0)
                    packet.setProperty("f", 128)
                    packet.setProperty("g", 128)
                    packet.setProperty("h", buffer)
                }
            }
            if (SettingConfig.sendMapAsync) {
                player.sendPacket(packet)
            } else {
                player.sendPacketBlocking(packet)
            }
        }
    }

    private fun getMainHandSlot(player: Player): Int {
        if (hand == Hand.OFF) {
            return 45
        }
        return player.inventory.heldItemSlot + 36
    }
}
