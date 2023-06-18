package com.example.hallo.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hallo.R
import com.example.hallo.models.InputComposable
import com.example.hallo.models.Message
import com.example.hallo.ui.composables.InputComponent
import com.example.hallo.ui.screens.ui.theme.HalloTheme
import com.example.hallo.ui.theme.BackgroundDark
import com.example.hallo.ui.theme.PrimaryBlue
import com.example.hallo.ui.theme.PrimaryPink
import com.example.hallo.ui.theme.PrimaryYellow
import com.example.hallo.ui.theme.TextWhite
import com.example.hallo.viewModels.ChatViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualChatScreen(
    viewModel: ChatViewModel = viewModel(),
    navigateBack: () -> Unit,
    chatId: String?,
    title: String?
) {

//    DisposableEffect(Unit) {
//        Log.d("AAAAAA", chatId.toString())
////        profileViewModel.getProfileData()
////        viewModel.getConversations()
//        onDispose {  }
//    }

    var newMessage by remember {
        mutableStateOf("")
    }

    val dummyData = listOf<Message>(
        Message(
            from = "Vian", message = "Hey how are you?", fromUserId = "Vian"
        ),
        Message(
            from = "Other user", message = "No man lekker and you?", fromUserId = "Other user"
        )
    )

    var allMessages = viewModel?.messageList ?: listOf<Message>()

    var currentUserFrom = "Vian"

    val isChatIdNotBlank = chatId.isNullOrBlank()

    LaunchedEffect(key1 = Unit) {
        Log.d("AAA Launch Effect attemp", chatId.toString())
        if(!isChatIdNotBlank) {
            viewModel.getRealtimeMessages(chatId ?: "")
            Log.d("AAA Launch Effect attemp", "....")
        } else {
            Log.d("AAA Launch Effect attemp failed", "....")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .height(20.dp)
                .drawBehind {
                    val strokeWidth = 2f
                    val x = size.width - strokeWidth
                    val y = size.height - strokeWidth

                    //bottom line
                    drawLine(
                        color = PrimaryPink,
                        start = Offset(0f, y),// bottom-left point of the box
                        end = Offset(x, y),// bottom-right point of the box
                        strokeWidth = strokeWidth
                    )
                }
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {navigateBack.invoke()}
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back",
                    tint = PrimaryYellow
                )
            }

            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )

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

            Text(
                text = title.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        val screenWidth = LocalConfiguration.current.screenWidthDp.dp

//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Red)
//        ) {
//
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f),
                reverseLayout = true,
                verticalArrangement = Arrangement.Bottom,
        ) {
            items(allMessages) { message ->
                if(viewModel.currentUserId == message.fromUserId) {
                    MessageTo(message)
                } else {
                    MessageFrom(message)
                }
            }
        }

            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = TextWhite,
                        focusedBorderColor = PrimaryPink,
                        unfocusedBorderColor = PrimaryBlue
                    ),
                    value = newMessage,
                    onValueChange = { newMessage = it },
                    label = {
                        Text(
                            "Type here",
                            color = TextWhite
                        )
                    }
                )

                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )

                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Icon",
                    tint = TextWhite,
                    modifier = Modifier
                        .clickable { viewModel.sendNewMessage(newMessage, chatId.toString()) }
                        .size(34.dp)
                )
            }
        }
    }
}

@Composable
fun MessageFrom(message: Message) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, PrimaryBlue, RoundedCornerShape(10.dp))
                .padding(12.dp)
        ) {
            Text(
                text = message.message,
                color = TextWhite,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun MessageTo(message: Message) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.End
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f),
            horizontalAlignment = Alignment.End
        ) {
            Column(
                modifier = Modifier
                    .border(1.dp, TextWhite, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = message.message,
                    color = TextWhite,
                    fontSize = 12.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Read",
                    tint = TextWhite
                )

                val inputTimeStamp = message.timestamp.toDate().toString().split("GMT")[0]
                val time = inputTimeStamp.substring(inputTimeStamp.length - 6, inputTimeStamp.length)

                Text(
                    text = time,
                    color = TextWhite,
                    fontSize = 12.sp
                )
            }
        }
    }
}

//@Preview(
//    showSystemUi = true
//)
//@Composable
//fun PreviewRegisterScreen() {
//    com.example.hallo.ui.theme.HalloTheme {
//        IndividualChatScreen(
//            navigateBack = {},
//            chatId = "chat1234",
//            title = it.arguments?.getString("title")
//        )
//    }
//}

