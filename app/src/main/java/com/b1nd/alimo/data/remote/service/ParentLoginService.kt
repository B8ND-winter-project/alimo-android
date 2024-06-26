package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.request.ParentLoginRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.ParentLoginResponse
import com.b1nd.alimo.di.url.AlimoUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class ParentLoginService @Inject constructor(
    private val httpClient: HttpClient
){
    suspend fun login(data: ParentLoginRequest) : BaseResponse<ParentLoginResponse> =
        httpClient.post(AlimoUrl.SIGN_IN){
            setBody(data)
        }.body()
}