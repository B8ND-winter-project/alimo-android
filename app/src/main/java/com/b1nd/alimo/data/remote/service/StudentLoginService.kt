package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.request.StudentLoginRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.studnet.StudentLoginResponse
import com.b1nd.alimo.di.AppHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

//interface StudentLoginService {
//
//    suspend fun login(data: StudentLoginRequest): Flow<Resource<BaseResponse<StudentLoginResponse>>>
//}

class StudentLoginService @Inject constructor(
    @AppHttpClient private val httpClient: HttpClient
){
    suspend fun login(data: StudentLoginRequest): BaseResponse<StudentLoginResponse> =
        httpClient.post("/sign-in/dodam"){
            setBody(data)
        }.body()
}