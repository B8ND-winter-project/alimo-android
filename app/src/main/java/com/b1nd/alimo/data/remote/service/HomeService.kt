package com.b1nd.alimo.data.remote.service

import android.util.Log
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.response.BaseResponse
import io.ktor.client.HttpClient
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random


class HomeService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getNotice(
        page: Int
    ): Resource<BaseResponse<List<NotificationModel>>> {
        Log.d("TAG", "getNotice: $page")
        return Resource.Success(dummyNotice(page))
    }

    private suspend fun dummyNotice(
        page: Int
    ): BaseResponse<List<NotificationModel>> {
        val nowPage = (15*page)
        val data = mutableListOf<NotificationModel>()
        for (i in nowPage..nowPage+14) {
            data.add(
                NotificationModel(
                    notificationId = i,
                    title = "$i i title",
                    content = "$i content",
                    speaker = Random.nextBoolean(),
                    createdAt = LocalDateTime.now(),
                    member = "$i member"
                )
            )
        }

        return BaseResponse(status = 200, message = "success", data = data)
    }
}