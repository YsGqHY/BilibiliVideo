package online.bingzi.bilibili.video.internal.indicator

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

/**
 * Server stage indicator
 * <p>
 * 服务器阶段指示器
 *
 * @constructor Create empty Server stage indicator
 *
 * @author BingZi-233
 * @since 2.0.0
 */
object ServerStageIndicator {
    /**
     * Server stge
     * <p>
     * 服务器阶段
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    var serverStage = LifeCycle.NONE

    /**
     * Const
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.CONST)
    fun const() {
        serverStage = LifeCycle.CONST
    }

    /**
     * Init
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.INIT)
    fun init() {
        serverStage = LifeCycle.INIT
    }

    /**
     * Load
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.LOAD)
    fun load() {
        serverStage = LifeCycle.LOAD
    }

    /**
     * Enable
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ENABLE)
    fun enable() {
        serverStage = LifeCycle.ENABLE
    }

    /**
     * Active
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.ACTIVE)
    fun active() {
        serverStage = LifeCycle.ACTIVE
    }

    /**
     * Disable
     *
     * @author BingZi-233
     * @since 2.0.0
     */
    @Awake(LifeCycle.DISABLE)
    fun disable() {
        serverStage = LifeCycle.DISABLE
    }
}