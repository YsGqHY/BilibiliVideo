package online.bingiz.bilibili.video.internal.helper

import org.bukkit.command.CommandSender
import taboolib.library.kether.LocalizedException
import taboolib.module.kether.KetherShell.eval
import taboolib.module.kether.ScriptOptions

fun List<String>.ketherEval(sender: CommandSender) {
    try {
        eval(this, ScriptOptions.builder().sender(sender).build())
    } catch (e: LocalizedException) {
        e.message?.let { sender.warningAsLang("KetherLocalizedException", it) }
    } catch (e: Throwable) {
        e.message?.let { sender.warningAsLang("KetherThrowable", it) }
    }
}

fun String.ketherEval(sender: CommandSender) {
    try {
        eval(this, ScriptOptions.builder().sender(sender).build())
    } catch (e: LocalizedException) {
        e.message?.let { sender.warningAsLang("KetherLocalizedException", it) }
    } catch (e: Throwable) {
        e.message?.let { sender.warningAsLang("KetherThrowable", it) }
    }
}