package online.bingiz.bilibili.video.internal.util

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.function.console
import taboolib.module.lang.sendInfo
import taboolib.module.lang.sendWarn
import taboolib.platform.util.sendInfo
import taboolib.platform.util.sendWarn

internal fun infoMessageAsLang(node: String) {
    console().sendInfo(node)
}

internal fun infoMessageAsLang(node: String, vararg args: Any) {
    console().sendInfo(node, *args)
}

internal fun warningMessageAsLang(node: String) {
    console().sendWarn(node)
}

internal fun warningMessageAsLang(node: String, vararg args: Any) {
    console().sendWarn(node, *args)
}

internal fun CommandSender.infoAsLang(node: String) {
    if (this is Player) {
        this.sendInfo(node)
    } else {
        infoMessageAsLang(node)
    }
}

internal fun CommandSender.infoAsLang(node: String, vararg args: Any) {
    if (this is Player) {
        this.sendInfo(node, *args)
    } else {
        infoMessageAsLang(node, *args)
    }
}

internal fun CommandSender.warningAsLang(node: String) {
    if (this is Player) {
        this.sendWarn(node)
    } else {
        warningMessageAsLang(node)
    }
}

internal fun CommandSender.warningAsLang(node: String, vararg args: Any) {
    if (this is Player) {
        this.sendWarn(node, *args)
    } else {
        warningMessageAsLang(node, *args)
    }
}