package online.bingzi.bilibili.video.internal.table

import online.bingzi.bilibili.video.internal.entity.ReceiveData
import org.ktorm.schema.*

object ReceiveTable : Table<ReceiveData>("bilibili_video_receive") {
    val uuid = uuid("player_uuid").primaryKey().bindTo { it.uuid }
    val name = varchar("player_name").bindTo { it.name }
    val bv = varchar("bv").bindTo { it.bv }
    val command = text("command").bindTo { it.command }
    val create = datetime("create").bindTo { it.create }
}
