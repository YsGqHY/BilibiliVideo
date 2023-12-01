package online.bingiz.bilibili.video.internal.helper

import org.bukkit.command.CommandSender
import taboolib.library.kether.LocalizedException
import taboolib.module.kether.KetherShell.eval
import taboolib.module.kether.ScriptOptions

/**
 * Kether eval
 * Kether 脚本
 *
 * @param sender 执行者
 */
fun String.ketherEval(sender: CommandSender) {
    evalScript(listOf(this), sender)
}

/**
 * Kether eval
 * Kether 脚本
 *
 * @param sender 执行者
 */
fun List<String>.ketherEval(sender: CommandSender) {
    evalScript(this, sender)
}

/**
 * Eval script
 * 脚本执行
 *
 * @param script 脚本
 * @param sender 执行者
 */
private fun evalScript(script: List<String>, sender: CommandSender) {
    try {
        eval(script, ScriptOptions.builder().sender(sender).build())
    } catch (e: LocalizedException) {
        e.message?.let { sender.warningAsLang("KetherLocalizedException", it) }
    } catch (e: Throwable) {
        e.message?.let { sender.warningAsLang("KetherThrowable", it) }
    }
}