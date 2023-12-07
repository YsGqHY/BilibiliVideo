package online.bingiz.bilibili.video.internal.cache

import taboolib.common5.Baffle
import java.util.concurrent.TimeUnit

val baffleCache = Baffle.of(1, TimeUnit.MINUTES)