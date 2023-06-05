package com.example.hallo.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.hallo.R
import com.example.hallo.models.ButtonComposable
import com.example.hallo.models.InputComposable
import com.example.hallo.ui.composables.ButtonComponent
import com.example.hallo.ui.composables.InputComponent
import com.example.hallo.ui.theme.BackgroundDark
import com.example.hallo.ui.theme.PrimaryPink
import com.example.hallo.ui.theme.TextBlue
import com.example.hallo.ui.theme.TextNonActive
import com.example.hallo.ui.theme.TextWhite


@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(R.drawable.login_screen_text_top),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Image(
                painter = painterResource(R.drawable.left_shape),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(screenWidth / 2)
                    .align(Alignment.BottomStart)
            )
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 35.dp),
            text = "Complete the form below to create your profile",
            color = TextWhite
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        Image(
            painter = painterResource(R.drawable.right_shape_pink),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(screenWidth / 2)
                .align(Alignment.End)
        )

        Spacer(
            modifier = Modifier
                .height(5.dp)
        )

        var email = ""
        var password = ""

        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
        ) {
            InputComponent(
                input = InputComposable(
                    label = "Email Address",
                    placeholder = "eg. johndoe@gmail.com",
                    keyboardType = KeyboardType.Email,
                    onChange = {email = it}
                )
            )

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            InputComponent(
                input = InputComposable(
                    label = "Password",
                    placeholder = "eg. johndoe123",
                    keyboardType = KeyboardType.Password,
                    onChange = {password = it},
                    isPasswordField = true
                )
            )
        }

        Spacer(
            modifier = Modifier
                .height(30.dp)
                .weight(1f, true)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp, vertical = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {

            ButtonComponent(
                button = ButtonComposable(
                    label = "Log In",
                    borderColor = PrimaryPink,
                    textColor = PrimaryPink
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account?",
                    color = TextNonActive,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )

                TextButton(
                    onClick = {navigateToRegister.invoke()}
                ) {
                    Text(
                        text = "Sign up",
                        color = TextBlue
                    )
                }
            }
        }
    }
}