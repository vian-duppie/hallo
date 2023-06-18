package com.example.hallo.models

import androidx.compose.ui.text.input.KeyboardType

data class InputComposable (
    val label: String = "label",
    val onChange: (String) -> Unit = {},
    val keyboardType: KeyboardType = KeyboardType.Text,
    val isPasswordField: Boolean = false,
    val placeholder: String = "",
    val value: String = "",
    val enabled: Boolean = true
)