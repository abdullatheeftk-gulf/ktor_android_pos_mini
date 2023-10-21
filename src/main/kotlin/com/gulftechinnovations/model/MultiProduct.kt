package com.gulftechinnovations.model

import kotlinx.serialization.Serializable

@Serializable
data class MultiProduct(
    var multiProductId:Int = 0,
    val parentProductId:Int,
    val multiProductName:String,
    val multiProductImage:String?=null,
    val noOfTimesOrdered:Int = 0,
    val info:String? = null
)
