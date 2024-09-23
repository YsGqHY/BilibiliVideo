package online.bingzi.bilibili.video.internal.table

import online.bingzi.bilibili.video.internal.entity.BindData
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar

object BindTable : Table<BindData>("bilibili_video_bind") {
    val uuid = uuid("player_uuid").primaryKey().bindTo { it.uuid }
    val name = varchar("player_name").bindTo { it.name }
    val bilibiliMid = varchar("blibili_mid").bindTo { it.bilibiliMid }
    val bilibiliName = varchar("bilibili_name").bindTo { it.bilibiliName }
}
