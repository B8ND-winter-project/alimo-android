package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.request.ParentLoginRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.ParentLoginResponse
import com.b1nd.alimo.di.AppHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

//interface ParentLoginService {
//    suspend fun login(data: ParentLoginRequest): Flow<Resource<BaseResponse<ParentLoginResponse>>>
//}

class ParentLoginService @Inject constructor(
    @AppHttpClient private val httpClient: HttpClient
){
    suspend fun login(data: ParentLoginRequest) : BaseResponse<ParentLoginResponse> =
        httpClient.post("/sign-in"){
            setBody(data)
        }.body()
}