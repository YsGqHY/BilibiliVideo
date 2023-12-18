package online.bingiz.bilibili.video.internal.cache

import online.bingiz.bilibili.video.internal.config.SettingConfig
import taboolib.common5.Baffle
import java.util.concurrent.TimeUnit

var baffleCache = Baffle.of(SettingConfig.cooldown, TimeUnit.SECONDS)