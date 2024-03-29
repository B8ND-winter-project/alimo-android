package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.local.entity.TokenEntity
import com.b1nd.alimo.data.model.TokenModel

internal fun TokenEntity?.toModel() =
    TokenModel(
        token = this?.token,
        refreshToken = this?.refreshToken
    )
