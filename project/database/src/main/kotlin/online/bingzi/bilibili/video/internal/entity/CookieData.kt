package online.bingzi.bilibili.video.internal.entity

import org.ktorm.entity.Entity
import java.util.*

interface CookieData : Entity<CookieData> {
    companion object : Entity.Factory<CookieData>()

    val uuid: UUID
    val name: String
    var sessData: String
    var biliJct: String
    var dedeUserID: String
    var dedeUserIDCkMd5: String
    var sid: String
}
