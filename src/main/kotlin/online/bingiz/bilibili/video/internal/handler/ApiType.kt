package online.bingiz.bilibili.video.internal.handler

/**
 * Api type
 *
 *
 * @constructor Create empty Api type
 */
enum class ApiType(val apiHandler: ApiHandler) {
    COINS(CoinsHandler()),
    FAVOURED(FavouredHandler()),
    FOLLOWING(FollowingHandler()),
    LIKE(LikeHandler());

    companion object {
        /**
         * Build handler
         * 构建处理器依据指定顺序
         *
         * @param apiType
         * @return
         */
        fun buildHandler(vararg apiType: ApiType): ApiHandler {
            requireNotNull(apiType) {
                error("至少需要一个 ApiType 来生成处理程序")
            }
            for (i in 0 until apiType.size - 1) {
                apiType[i].apiHandler.setNextHandler(apiType[i + 1].apiHandler)
            }
            return apiType[0].apiHandler
        }
    }
}
