package com.b1nd.alimo.data.remote.service

import android.util.Log
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.mapper.toModels
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.home.HomeSpeakerResponse
import com.b1nd.alimo.data.remote.response.notification.NotificationResponse
import com.b1nd.alimo.di.AppHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class HomeService @Inject constructor(
    @AppHttpClient private val httpClient: HttpClient
) {

    suspend fun getPost(
        page: Int,
        size: Int,
        category: String
    ): Resource<List<NotificationModel>> {
        return try {
            val body = httpClient.get("/notification/load") {
                parameter("page", page)
                parameter("size", size)
                parameter("category", if (category == "전체") "null" else category)
            }.body<BaseResponse<List<NotificationResponse>>>()
            Log.d("TAG", "getPost: $body")
            Resource.Success(body.data.toModels())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    suspend fun getSpeaker(): BaseResponse<HomeSpeakerResponse?> =
        httpClient.get("/notification/speaker") {

        }.body()
}