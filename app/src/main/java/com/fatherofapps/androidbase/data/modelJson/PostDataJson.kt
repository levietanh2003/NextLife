package com.fatherofapps.androidbase.data.modelJson

import com.fatherofapps.androidbase.data.models.PostData
import com.fatherofapps.androidbase.data.models.PromotionalPost

class PostDataJson(
    val currentPage: Int,
    val totalPages: Int,
    val pageSize: Int,
    val totalElements: Int,
    val data: List<PromotionalPostJson>
)
{

    fun toPostData(): PostData {
        return PostData(
            currentPage,
            totalPages,
            pageSize,
            totalElements,
            data.map { it.toPromotional() }
        )
    }
}
