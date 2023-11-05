package com.developersandgraphics.apuntedomino.back.models

data class ResponseClient<T>(
    val status: Boolean, val message: String, val data: T
)
