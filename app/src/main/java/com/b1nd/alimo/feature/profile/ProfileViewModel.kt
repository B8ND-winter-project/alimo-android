package com.b1nd.alimo.feature.profile

import com.b1nd.alimo.base.BaseViewModel

class ProfileViewModel: BaseViewModel() {

    fun onClickStudentCode() = viewEvent(ON_CLICK_STUDENT_CODE)

    fun onClickPrivatePolicy() = viewEvent(ON_CLICK_PRIVATE_POLICY)

    fun onClickServicePolicy() = viewEvent(ON_CLICK_SERVICE_POLICY)

    fun onClickLogout() = viewEvent(ON_CLICK_LOGOUT)

    companion object {
        const val ON_CLICK_STUDENT_CODE = "ON_CLICK_STUDENT_CODE"
        const val ON_CLICK_PRIVATE_POLICY = "ON_CLICK_PRIVATE_POLICY"
        const val ON_CLICK_SERVICE_POLICY = "ON_CLICK_SERVICE_POLICY"
        const val ON_CLICK_LOGOUT = "ON_CLICK_LOGOUT"
    }
}