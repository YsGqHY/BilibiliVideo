package online.bingiz.bilibili.video.internal.engine.drive

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface UpdateDrive {
    @GET("main/gradle.properties")
    fun getVersion(): Call<ResponseBody>
}