package online.bingzi.bilibili.video.internal.entity

import org.ktorm.entity.Entity
import java.util.*

interface BindData : Entity<BindData> {
    companion object : Entity.Factory<BindData>()

    val uuid: UUID
    val name: String
    var bilibiliMid: String
    var bilibiliName: String
}
