package com.b1nd.alimo.data.remote.response.detail

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SubCommentResponse(
    @SerializedName("commentId")
    val commentId: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("commentor")
    val commenter: String,
    @SerializedName("commenterId")
    val commenterId: Int,
    @SerializedName("createdAt")
    val createdAt: LocalDateTime,
    @SerializedName("profileImage")
    val profileImage: String?,
)