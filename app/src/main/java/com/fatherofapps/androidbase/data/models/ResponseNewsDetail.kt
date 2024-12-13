package com.fatherofapps.androidbase.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseNewsDetail(val responseCode: Int,
                              val data: NewsData,
                              val message: String,
                              )
