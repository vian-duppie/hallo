package com.example.hallo.models

import androidx.compose.ui.graphics.Color
import com.example.hallo.ui.theme.PrimaryBlue

data class ButtonComposable(
    val onClick: () -> Unit = {},
    val borderColor: Color = PrimaryBlue,
    val label: String = "Placeholder",
    val textColor: Color = PrimaryBlue
)