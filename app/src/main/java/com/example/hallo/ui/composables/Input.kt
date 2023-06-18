package com.example.hallo.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.hallo.models.InputComposable
import com.example.hallo.ui.theme.Active
import com.example.hallo.ui.theme.NonActive
import com.example.hallo.ui.theme.TextNonActive
import com.example.hallo.ui.theme.TextWhite

@Composable
fun InputComponent(
    input: InputComposable
) {
    Column(

    ) {
        Text(
            text = input.label,
            fontSize = 15.sp,
            color = TextWhite
        )

        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
        )

        // Input value of textField
        var value by remember {
            mutableStateOf("")
        }

        // Check if Input is Focused
        var inputFocused = remember {
            mutableStateOf(false) // Not focused at first
        }

        var isPassword = input.isPasswordField

        BasicTextField(
            enabled = input.enabled,
            value = input.value,
            onValueChange = {
                value = it
                input.onChange(it)
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = TextWhite
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation =
            if (isPassword)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            modifier = Modifier
                .onFocusChanged {
                    inputFocused.value = it.isFocused
                },
            cursorBrush = SolidColor(Active),
            decorationBox = {innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = if (inputFocused.value) Active else NonActive,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2.dp.toPx()
                            )
                        }
                        .padding(bottom = 5.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = input.placeholder,
                            fontSize = 12.sp,
                            color = TextNonActive
                        )
                    }
                    innerTextField()
                }
            }
        )
    }

}