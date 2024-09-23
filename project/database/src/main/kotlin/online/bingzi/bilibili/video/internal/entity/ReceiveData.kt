package online.bingzi.bilibili.video.internal.entity

import org.ktorm.entity.Entity
import java.time.LocalDateTime
import java.util.*

interface ReceiveData : Entity<ReceiveData> {
    companion object : Entity.Factory<ReceiveData>()

    val uuid: UUID
    val name: String
    val bv: String
    val command: String
    val create: LocalDateTime
}
