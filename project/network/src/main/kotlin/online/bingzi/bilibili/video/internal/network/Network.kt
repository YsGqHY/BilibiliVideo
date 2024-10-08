package online.bingzi.bilibili.video.internal.network

import online.bingzi.bilibili.video.internal.network.service.Buvid3Service
import online.bingzi.bilibili.video.internal.network.service.LoginService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency

@RuntimeDependencies(
    RuntimeDependency(value = "!com.squareup.retrofit2:retrofit:2.11.0"),
    RuntimeDependency(value = "!com.squareup.retrofit2:converter-gson:2.11.0"),
)
object Network {
    private const val PASSPORT_BASE_URL = "https://passport.bilibili.com"
    private const val API_BASE_URL = "https://api.bilibili.com"

    val loginService = Retrofit.Builder()
        .baseUrl(PASSPORT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LoginService::class.java)

    val buvid3Service = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Buvid3Service::class.java)
}
