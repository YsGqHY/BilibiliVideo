package online.bingzi.bilibili.video.internal.entity

import com.comphenix.protocol.wrappers.EnumWrappers

/**
 * Hand enum
 * <p>
 * 手部枚举
 *
 * @constructor Create empty Hand enum
 *
 * @author BingZi-233
 * @since 2.0.0
 */
enum class HandEnum(val wrapper: EnumWrappers.ItemSlot) {
    /**
     * Main hand
     * <p>
     * 主手
     *
     * @constructor Create empty Main
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    MAIN_HAND(EnumWrappers.ItemSlot.MAINHAND),

    /**
     * Off hand
     * <p>
     * 副手
     *
     * @constructor Create empty Off
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    OFF_HAND(EnumWrappers.ItemSlot.OFFHAND)
}