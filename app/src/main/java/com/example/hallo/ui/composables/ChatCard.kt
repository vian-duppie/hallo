package com.example.hallo.ui.composables

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hallo.R
import com.example.hallo.models.ChatCardComposable
import com.example.hallo.ui.composables.ui.theme.HalloTheme
import com.example.hallo.ui.theme.PrimaryPink
import com.example.hallo.ui.theme.TextWhite

@Composable
fun ChatCard(
    data: ChatCardComposable
) {
    DisposableEffect(Unit) {
        Log.d("CHATTTTT", data.toString())
        Log.d("CHATTTTT", data.toString())

        onDispose {  }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                PrimaryPink,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
            .height(IntrinsicSize.Min)
            .clickable { data.onClick.invoke() }
    ) {
        Image(
            painter = painterResource(R.drawable.download),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .clip(CircleShape)
        )

        Spacer(
            modifier = Modifier
                .width(5.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text=data.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextWhite
            )


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(18.dp),
                    imageVector = Icons.Default.Done,
                    contentDescription = "",
                    tint = TextWhite
                )

                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                )

                Text(
                    text=data.last_message,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextWhite,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(
            modifier = Modifier
                .width(20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "10:30",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .width(IntrinsicSize.Min)
                        .background(PrimaryPink, shape = CircleShape)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "99+",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = TextWhite,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
