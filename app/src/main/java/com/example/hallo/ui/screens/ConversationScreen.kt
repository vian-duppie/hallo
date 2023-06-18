package com.example.hallo.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.hallo.R
import com.example.hallo.models.ChatCardComposable
import com.example.hallo.models.Conversation
import com.example.hallo.ui.composables.ChatCard
import com.example.hallo.ui.theme.BackgroundDark
import com.example.hallo.ui.theme.PrimaryYellow
import com.example.hallo.viewModels.ChatViewModel
import com.example.hallo.viewModels.ConversationsViewModel
import com.example.hallo.viewModels.ProfileViewModel

@Composable
fun ConversationScreen(
    navigateToProfile: () -> Unit,
    navigateToChat: (String, String) -> Unit,
    viewModel: ConversationsViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    chatViewModel: ChatViewModel = viewModel()
) {
    val profileUiState = profileViewModel.profileUiState
    val allConversations = viewModel.convoList ?: listOf<Conversation>()

    DisposableEffect(Unit) {
        profileViewModel.getProfileData()
        viewModel.getConversations()
        onDispose {  }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(top = 30.dp),
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

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(profileUiState.profileImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.download),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, PrimaryYellow, CircleShape)
                    .clickable { navigateToProfile.invoke() }
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

        Log.d("AAAAAAAAAAAAAAA", allConversations.toString())

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(allConversations) { conversation ->
                ChatCard(
                    ChatCardComposable(
                        title = conversation.title,
                        onClick = {navigateToChat.invoke(conversation.id, conversation.title)},
                        last_message = conversation.last_message,
                    )
                )
            }
        }
    }
}