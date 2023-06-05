package com.example.hallo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.containerColor
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.hallo.R
import com.example.hallo.ui.theme.BackgroundDark
import com.example.hallo.ui.theme.PrimaryYellow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import com.example.hallo.ui.theme.PrimaryPink

@Composable
fun ConversationScreen() {
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

            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = "Icon",
                tint = PrimaryYellow,
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(60.dp)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                painter = painterResource(R.drawable.download),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, PrimaryYellow, CircleShape)
            )

            Spacer(
                modifier = Modifier
                    .width(35.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .height(40.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Image(
                painter = painterResource(R.drawable.conversation_screen_text_top),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(horizontal = 35.dp)
            )

            Spacer(
                Modifier
                    .height(5.dp)
            )

            Image(
                painter = painterResource(R.drawable.conversation_screen_shape_left),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        }

        Spacer(
            Modifier
                .height(30.dp)
        )

        var tabIndex by remember { mutableStateOf(0) }
        val tabs = listOf("Chats", "Group")

        TabRow(
            containerColor = Color.Transparent,
            selectedTabIndex = tabIndex,
            divider = {},
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(2.dp)
                        .padding(horizontal = 65.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryPink)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = index == tabIndex,
                    onClick = { tabIndex = index },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = title,
                        style = TextStyle(
                            color = PrimaryPink,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
        when (tabIndex) {
            0 -> IndividualChats()
            1 -> GroupChats()
        }
    }
}