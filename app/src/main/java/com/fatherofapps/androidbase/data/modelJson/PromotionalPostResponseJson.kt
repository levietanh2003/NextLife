package com.fatherofapps.androidbase.data.modelJson

import com.fatherofapps.androidbase.data.models.PromotionalPostResponse

class PromotionalPostResponseJson(
    val responseCode: Int,
    val data: PostDataJson,
    val message: String
)

{
    fun toPromotionalPost() : PromotionalPostResponse {
        return PromotionalPostResponse(
            responseCode,
            data = data.toPostData(),
            message = message
        )
    }
}