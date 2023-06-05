package com.example.hallo.ui.screens

import android.util.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.hallo.R
import com.example.hallo.models.InputComposable
import com.example.hallo.ui.composables.InputComponent
import com.example.hallo.ui.theme.BackgroundDark
import com.example.hallo.ui.theme.PrimaryPink
import com.example.hallo.ui.theme.SecondaryGreen
import com.example.hallo.ui.theme.TextWhite
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ProfileScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .width(35.dp)
            )

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "",
                    tint = SecondaryGreen,
                    modifier = Modifier
                        .size(48.dp)
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                painter = painterResource(R.drawable.profile_screen_text_top),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                painter = painterResource(R.drawable.profile_screen_right_shape),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
            )
        }

        Spacer(
            modifier = Modifier
                .height(40.dp)
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                drawArc(
                    color = PrimaryPink,
                    startAngle = 0f,
                    sweepAngle = 270f,
                    useCenter = false,
                    topLeft = Offset.Zero,
                    size = this.size,
                    style = Stroke(width = 9.dp.toPx())
                )
            }

            Image(
                painter = painterResource(R.drawable.download),
                contentDescription = "Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )

            Icon(
                painter = painterResource(id = R.drawable.icon_camera),
//                imageVector = R.drawable.icon_camera,
                contentDescription = "Icon",
                tint = Color.White,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }

        Spacer(
            modifier = Modifier
                .height(15.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
        ) {
            InputComponent(
                input = InputComposable(
                    label = "About",
                    placeholder = "Available",
                    keyboardType = KeyboardType.Text,
//                    onChange = {email = it}
                )
            )

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            InputComponent(
                input = InputComposable(
                    label = "Username",
                    placeholder = "Vian du Plessis",
                    keyboardType = KeyboardType.Text,
//                    onChange = {password = it},
                )
            )

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            InputComponent(
                input = InputComposable(
                    label = "Email",
                    placeholder = "test@gmail.com",
                    keyboardType = KeyboardType.Email,
//                    onChange = {password = it},
                )
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 35.dp)
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(
                    text = "Logout Profile",
                    color = TextWhite
                )

                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )

                Icon(
                    imageVector = Icons.Outlined.ExitToApp,
                    contentDescription = "Icon",
                    tint = Color.White,
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Red
                )
            ) {
                Text(
                    text = "Delete Account",
                    color = TextWhite
                )

                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )

                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Icon",
                    tint = Color.White,
                )
            }
        }
    }
}