package com.example.hallo.models

data class ChatCardComposable(
    val title: String = "",
    val onClick:() -> Unit = {},
    val last_message: String = ""
)