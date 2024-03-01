package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.makeApiGetRequest
import com.b1nd.alimo.data.remote.makeApiPostRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.Response
import com.b1nd.alimo.data.remote.response.profile.ProfileCategoryResponse
import com.b1nd.alimo.data.remote.response.profile.ProfileInfoResponse
import com.b1nd.alimo.data.remote.service.ProfileService
import com.b1nd.alimo.di.AppHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ProfileRepository @Inject constructor(
    @AppHttpClient private val httpClient: HttpClient
): ProfileService {
    private val testToken = "eyJKV1QiOiJBQ0NFU1MiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiQXV0aG9yaXphdGlvbiI6IlRFTVAiLCJpYXQiOjE3MDY2MTQwODcsImV4cCI6MTcwNjYxNTg4N30.1v8MifrBvkb25PJTYwzQ_bGd6VBwt0Bbvntd1Yw-pM-0Y4VqO574DoE-OFo_S8ktCP8BejHVGKMeyeXEatgmzQ"

    override suspend fun getInfo(): Flow<Resource<BaseResponse<ProfileInfoResponse>>> =
        makeApiGetRequest(httpClient, "/member/info") {
            headers {
                header("Authorization", "Bearer $testToken")
            }
        }

    override suspend fun getCategory(): Flow<Resource<BaseResponse<ProfileCategoryResponse>>> =
        makeApiGetRequest(httpClient, "/member/role-list") {
            headers {
                header("Authorization", "Bearer $testToken")
            }
        }

    override suspend fun setAlarmState(value: Boolean): Flow<Resource<Response>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/member/alarm-on-off"
        ){
            headers{
                header("Authorization","Bearer $testToken")
            }
            parameter("is_off_alarm", value)
        }

}