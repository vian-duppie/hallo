package com.example.hallo.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallo.models.ButtonComposable
import com.example.hallo.ui.theme.PrimaryBlue
import com.example.hallo.ui.theme.TextWhite

@Composable
fun ButtonComponent(
    button: ButtonComposable
) {
    OutlinedButton(
        onClick = {button.onClick.invoke()},
        border = BorderStroke(3.dp, button.borderColor),
        shape = RoundedCornerShape(30)
    ) {
        Text(
            text = button.label,
            color = button.textColor,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(
                    bottom = 5.dp,
                    top = 5.dp
                )
        )
    }
}