package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.StudentLoginModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.request.StudentLoginRequest
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.StudentLoginService
import javax.inject.Inject

class StudentLoinRepository @Inject constructor(
    private val studentLoginService: StudentLoginService
) {
   suspend fun login(data: StudentLoginRequest) =
       safeFlow<StudentLoginModel> {
           val response = studentLoginService.login(data).errorCheck()
           emit(
               Resource.Success(
                   response.data.toModel()
               )
           )
       }

}