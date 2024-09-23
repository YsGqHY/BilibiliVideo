package online.bingzi.bilibili.video.internal.table

import online.bingzi.bilibili.video.internal.entity.CookieData
import org.ktorm.schema.Table
import org.ktorm.schema.text
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar

object CookieTable : Table<CookieData>("bilibili_video_cookie") {
    val uuid = uuid("player_uuid").primaryKey().bindTo { it.uuid }
    val name = varchar("player_name").bindTo { it.name }
    var sessData = text("sess_data").bindTo { it.sessData }
    var biliJct = text("bili_jct").bindTo { it.biliJct }
    var dedeUserID = text("dede_user_id").bindTo { it.dedeUserID }
    var dedeUserIDCkMd5 = text("dede_user_id_ck_md5").bindTo { it.dedeUserIDCkMd5 }
    var sid = text("sid").bindTo { it.sid }
}
